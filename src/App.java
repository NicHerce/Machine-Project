/* 
    Medical Laboratory Information System 
*/

// Essentially Utilities will be placed in Utilities.java, 
// Patient in Patients.java (Maybe we can get away with putting it in Patient.java),
// ETC... ETC...

/*/
 *  Aian ( 05/16/22 ): Optimized and shortened
/*/
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.MalformedURLException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class App {

    static Scanner input = new Scanner(System.in);
    private static ArrayList<Patient> patientRecords = new ArrayList<Patient>();
    private static ArrayList<LabResults> requestRecords = new ArrayList<LabResults>();
    private static ArrayList<Service> serviceRecords = new ArrayList<Service>();

    public static void main(String[] args) throws Exception {
        String answer;

        // Reads Patients.txt file to get records
        readRecord();

        do{

            mainMenu();
            
            // Ask user if they want to make another transaction
            System.out.print("\nReturn to Main Menu? [Y/N]: ");
            answer = input.next(); input.nextLine();
        }while("y".equalsIgnoreCase(answer));
        
        // Write records to Patients.txt file
        writeRecord();
    }

/********************************************************************************************************************************************/
    /*/
     *  Utilities
    /*/

    // Reads Patients.txt file to get records
    public static void readRecord() {
        
        // reads Patients.txt file
        try {
            Scanner scanner = new Scanner(new File("Patients.txt"));
            String line = "";    // stores a line in the records
            String parts[] = {}; // array of String to store the parts of the line

            while(scanner.hasNextLine()) {
                line = scanner.nextLine();      // stores a line from Patients.txt file
                parts = line.split(";"); // separates the line into parts

                // assigns the portions of the String for the parameters of Patient object
                String uID = parts[0];
                String lastName = parts[1];
                String firstName = parts[2];
                String middleName = parts[3];
                long birthday = Long.parseLong(parts[4]);
                String gender = parts[5];
                String address = parts[6];
                String phoneNum = parts[7];
                String nID = parts[8];
                Boolean deleted = false;
                String reason = "";

                // if patient is deleted, update the variables
                if (parts.length > 9) {
                    if ("D".equals(parts[9])) deleted = true;
                    reason = parts[10];

                    System.out.printf("%s;%s;%s;%s;%d;%s;%s;%s;%s;%s;%s;\n", 
                        uID, lastName, firstName, middleName, birthday, gender, address, phoneNum, nID, parts[9], reason
                    );

                } else {
                    // check record
                    System.out.printf("%s;%s;%s;%s;%d;%s;%s;%s;%s;\n", 
                        uID, lastName, firstName, middleName, birthday, gender, address, phoneNum, nID  
                    );
                }

                // add new Patient object to patientRecords ArrayList
                patientRecords.add(new Patient(uID, lastName, firstName, middleName, birthday, gender, address, phoneNum, nID, deleted, reason));
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

        System.out.println("");
        
        // reads Services.txt file
        try {
            Scanner scanner = new Scanner(new File("Services.txt"));
            String line = "";    // stores a line in the records
            String parts[] = {}; // array of String to store the parts of the line

            while(scanner.hasNextLine()) {
                line = scanner.nextLine();      // stores a line from Patients.txt file
                parts = line.split(";"); // separates the line into parts

                // assigns the portions of the String for the parameters of Patient object
                String serviceCode = parts[0];
                String description = parts[1];
                double price = Double.parseDouble(parts[2]);
                Boolean deleted = false; // parts[3]
                String reason = "";      // parts[4]

                // // if patient is deleted, update the variables
                if (parts.length > 3) {
                    if ("D".equals(parts[3])) deleted = true;
                    reason = parts[4];

                    System.out.printf("%s;%s;%.2f;D;%s;\n", serviceCode, description, price, reason);

                } else {
                    // check record
                    System.out.printf("%s;%s;%.2f;\n", serviceCode, description, price);
                }

                // add new Service object to serviceRecords ArrayList
                serviceRecords.add(new Service(serviceCode, description, price, deleted, reason));
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    }
    
    // Write records to Patients.txt and Services.txt file
    public static void writeRecord() {
        
        // write Patients.txt file
        try {
            Writer writer = new BufferedWriter(new FileWriter("Patients.txt", false));
            
            for (Patient patient : patientRecords) {
                // patient is not deleted
                if (patient.getIsDeleted() == false) {
                    writer.write(
                        patient.getUID() + ";" +
                        patient.getLastName() + ";" +
                        patient.getFirstName() + ";" +
                        patient.getMiddleName() + ";" +
                        patient.getBirthday() + ";" +
                        patient.getGender() + ";" +
                        patient.getAddress() + ";" +
                        patient.getPhoneNum() + ";" +
                        patient.getNationalID() + ";\n"
                    );
                    
                } else { // patient is deleted
                    writer.write(
                        patient.getUID() + ";" +
                        patient.getLastName() + ";" +
                        patient.getFirstName() + ";" +
                        patient.getMiddleName() + ";" +
                        patient.getBirthday() + ";" +
                        patient.getGender() + ";" +
                        patient.getAddress() + ";" +
                        patient.getPhoneNum() + ";" +
                        patient.getNationalID() + ";D;" +
                        patient.getReason() + ";\n"
                    );
                }
                
            }

            writer.close();
            System.out.println("\nSuccessfully wrote to Patients file");
            
        } catch(IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        // write Services.txt file
        try {
            Writer writer = new BufferedWriter(new FileWriter("Services.txt", false));
            
            for (Service service : serviceRecords) {
                // service is not deleted
                if (service.getIsDeleted() == false) {
                    writer.write(
                        service.getServiceCode() + ";" +
                        service.getDescription() + ";" +
                        service.getPrice() + ";\n"
                    );
                    
                } else { // service is deleted
                    writer.write(
                        service.getServiceCode() + ";" +
                        service.getDescription() + ";" +
                        service.getPrice() + ";D;" +
                        service.getReason() + ";\n"
                    );
                }
                
            }

            writer.close();
            System.out.println("Successfully wrote to Services file\n");
            
        } catch(IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }

    // Main Menu of the Medical Laboratory Information System 
    public static void mainMenu() throws MalformedURLException, IOException, DocumentException {
        
        clear();

        int answer;

        // Main Menu        
        System.out.print (
            "Medical Laboratory Information System\n" +
            "[1] Manage Patient Records\n" +
            "[2] Manage Services\n" +
            "[3] Manage Laboratory Results\n\n" +
            "Select a transaction: "
        );

        // Checks whether the user's input is within the choices:
        answer = checkInput(1, 3);

        // Executes based on the answer of the user:
        switch(answer) {
            case 1: managePatient(); break;
            case 2: manageService(); break;
            case 3: manageRequests(); break;
        }
    }


    // Checks whether the user's input is within the choices:
    public static int checkInput(int limit1, int limit2) {
        int answer;
        
        do{
            answer = input.nextInt(); input.nextLine();
            
            if(answer < limit1 || answer > limit2 )
                System.out.print("Invalid input.\n\nSelect a transaction: ");

        }while(answer < limit1 || answer > limit2);

        return answer;
    }

    public static String checkAnswer() {
        String answer;

        do{
            answer = input.next(); input.nextLine();
            if(!"Y".equalsIgnoreCase(answer) && !"N".equalsIgnoreCase(answer))
                System.out.println("Invalid input.");
        } while (!"Y".equalsIgnoreCase(answer) && !"N".equalsIgnoreCase(answer)); 
        
        return answer;
    }

    // Checks if user input is in records ArrayList 
    public static Boolean filterSearch(String answer, Patient patient) {
        if( answer.equalsIgnoreCase(patient.getUID()) || 
            answer.equalsIgnoreCase(patient.getLastName()) || 
            answer.equalsIgnoreCase(patient.getFirstName()) || 
            answer.equalsIgnoreCase(patient.getMiddleName()) || 
            answer.equals(String.valueOf(patient.getBirthday())) ||
            answer.equals(String.valueOf(patient.getNationalID())) ) {
            // Search success
            return true;
        }
        
        // Search failed
        return false;
    }

    // Print patient's PDF file
    public static void printPDF(Patient patient) throws MalformedURLException, IOException, DocumentException {
        // age of patient 
        Calendar currentDate = new GregorianCalendar();
        long age = currentDate.get(Calendar.YEAR) - (patient.getBirthday() / 10000);
        if( currentDate.get(Calendar.MONTH) < patient.getBirthday()/100%100 || 
            currentDate.get(Calendar.MONTH) == patient.getBirthday()/100%100 && 
            currentDate.get(Calendar.DAY_OF_MONTH) < patient.getBirthday()%10) {
            age--;
        } 

        // full name of patient
        String fullname = patient.getLastName() + ", " + patient.getFirstName() + " " + patient.getMiddleName();
            
        try {
            
            // create document object
            Document doc = new Document();

            // create PdfWriter object for writing content
            String fileName = patient.getLastName() + "_" + "Request UID" + ".pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));

            // open the Document 
            doc.open();

            ArrayList<LabResults> labResults = returnRequests(patient.getUID());

            // create new image object with filename of the company logo
            Image img = Image.getInstance("logo.png");
            Image line = Image.getInstance("line.png");

            
            // adjust size of image and align it to the center
            img.scaleAbsolute(60f, 60f);
            img.setAlignment(Element.ALIGN_CENTER);
            line.scaleAbsolute(700f, 25f);
            line.setAlignment(Element.ALIGN_CENTER);

            // create new paragraph object
            Paragraph text = new Paragraph("Raccoon City, USA\nTelephone: (8)949533");
            text.setAlignment(Element.ALIGN_CENTER);

            // create table
            PdfPTable table = new PdfPTable(2);
            PdfPCell cell = new PdfPCell();
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            // cell.setColspan(4);
            
            // full name of patient
            cell.setPhrase(new Paragraph("Name: " + fullname));
            table.addCell(cell);

            // speciment ID
            cell.setPhrase(new Paragraph("Speciment ID: "));
            table.addCell(cell);
            
            // patient ID
            cell.setPhrase(new Paragraph("Patient ID: " + patient.getUID()));
            table.addCell(cell);
            
            // collection date of specimen
            cell.setPhrase(new Paragraph("Collection Date: "));
            table.addCell(cell);

            // age of patient 
            cell.setPhrase(new Paragraph("Age: " + age));
            table.addCell(cell);

            // birthday of patient 
            cell.setPhrase(new Paragraph("Birthday: " + patient.getBirthday()));
            table.addCell(cell);

            // gender of patient 
            cell.setPhrase(new Paragraph("Gender: " + patient.getGender()));
            table.addCell(cell);

            // phone number of patient
            cell.setPhrase(new Paragraph(String.format("Phone Number: %011d", Long.parseLong(patient.getPhoneNum()))));
            table.addCell(cell);

            // create test and result table
            PdfPTable tableTwo = new PdfPTable(2);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);

            // table headers
            cell.setPhrase(new Paragraph("Test"));
            tableTwo.addCell(cell);

            cell.setPhrase(new Paragraph("Result"));
            tableTwo.addCell(cell);

            // for loop for table contents
            for ( LabResults lbr : labResults )
            {
                cell.setPhrase(new Paragraph( lbr.getServiceCode() ));
                tableTwo.addCell(cell);
                cell.setPhrase(new Paragraph( lbr.getResults() ));
                tableTwo.addCell(cell);
            }

            // add the contents to the document
            doc.add(img);
            doc.add(text);
            doc.add(line);
            doc.add(table);
            doc.add(line);
            doc.add(tableTwo);
            doc.add(line);

            // close the document
            doc.close();

            // File successfully written
            System.out.println("PDF file created");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*/
     * Call clear() to clear current terminal screen
     * Aian (05/17/22): Added this.
    /*/
    public static void clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /*/
     *  Visual Loading System
     *  Creates ellipses per each 500 milisecond "tick" 
     *  Always ends with a new line
     *  Aian (05/17/22): Added this.
    /*/
    public static void loading( int ticks )
    {
        for ( int i = 0; i < ticks; i++ )
        {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            System.out.print(".");
            
        }
        System.out.println("");
    }

    /*/
     *  Formats Number to a Alpha Numeric Numbering System 
     *  Aian ( 05/18/22 ): Moved it and made this reusable
    /*/
    public static String formatValue(int input, int digits, int letters) {
        var ret_val = new StringBuilder();

        for ( int i = 0; i < digits; i++ )
        {
            ret_val.insert(0, input % 10);
            input /= 10;
        }
        for ( int i = 0; i < letters; i++ )
        {
            ret_val.insert(0, (char)('A' + (input % 26)));
            input /= 26;
        }

        return ret_val.toString();
    }

/********************************************************************************************************************************************/
    /*/
     *  Patient System
    /*/

    // Manage Patient Records
    public static void managePatient() throws MalformedURLException, IOException, DocumentException {
        clear();
        
        int answer;

        System.out.print (
            "Manage Patient Records\n" +
            "[1] Add New Patient\n" +
            "[2] Edit Patient Record\n" +
            "[3] Delete Patient Record\n" +
            "[4] Search Patient Record\n" +
            "[0] Return to Main Menu\n\n" +
            "Select a transaction: "
        );

        answer = checkInput(0, 4);

        switch(answer) {
            case 0: mainMenu(); break;
            case 1: addPatient(); break;
            case 2: editPatient(); break;
            case 3: deletePatient(); break;
            case 4: searchPatient(); break;
        }
    }

    // Add New Patient
    public static void addPatient() {
        clear();

        String answer;
        int patient_num = patientRecords.size() - 1;
        String uID;
        int day = Calendar.getInstance().get(Calendar.DATE);

        // If it is the first day of the month, reset patient_num to 0 (AAA00)
        // Else, increase the patient_num
        if(day == 1) patient_num = 0;
        else patient_num++;
        
        uID = generatepUID(patient_num); // Create UID for New Patient

        System.out.print("Last Name: ");
        String pLastName = input.nextLine();

        System.out.print("First Name: ");
        String pFirstName = input.nextLine();

        System.out.print("Middle Name: ");
        String pMiddleName = input.nextLine();

        System.out.print("Birthday (YYYYMMDD): ");
        long pBirthday = input.nextLong(); input.nextLine();

        System.out.print("Gender (M/F): ");
        String pGender = input.nextLine();

        System.out.print("Address: ");
        String pAddress = input.nextLine();

        System.out.print("Phone Number: ");
        String pPhoneNum = input.nextLine();

        System.out.print("National ID Number: ");
        String pNationalID = input.nextLine();

        // Save Patient Record:
        System.out.print("Save Patient Record [Y/N]? ");
        answer = checkAnswer();
        
        if(answer.equalsIgnoreCase("y")) 
        {
            patientRecords.add(new Patient(uID, pLastName, pFirstName, pMiddleName, pBirthday, pGender, pAddress, pPhoneNum, pNationalID, false, ""));
        }

        // Aian (05/17/22): Replaced the thing above with this.
        System.out.println(uID + ";" + pLastName + ";" + pFirstName + ";" + pMiddleName + ";" + String.valueOf(pBirthday) + ";" + pGender + ";" + pAddress + ";" + String.valueOf(pPhoneNum) + ";" + String.valueOf(pNationalID) + ";");

        // Aian (05/17/22): 
        if ( answer.equalsIgnoreCase("y") )
        {
            System.out.print("Successfully added patient");
            loading(3);
        }
        else
        {
            System.out.print("Patient not added");
            loading(3);
        }
    }

    // Edit Patient
    public static void editPatient() {
        clear();
        
        String answer; // user's input to [Y/N] prompt
        String info;   // info of patient   
        int found;     // counter for number of results
            
        do {
            // reset answer and found variable at start of loop
            answer = "N";
            found = 0;

            // input for searching patient in the records
            System.out.print("\nEnter Patient Information: ");
            info = input.nextLine();

            // loops through the records to look for patient
            for (Patient patient : patientRecords) {
                if (filterSearch(info, patient)) found++; // counts number of patient's with the same data as input     
            }

            // if multiple patients detected, outputs row if there is no header.
            if (found > 1) {
                // header row
                System.out.printf("%-13s %-10s %-10s %-11s %-9s %-8s %-15s %-12s %-10s\n",
                    "Patient's UID", "Last Name", "First Name", "Middle Name",
                    "Birthday", "Gender", "Address", "Phone Number", "National ID no."
                );

                // display list of multiple patients
                for (Patient patient : patientRecords) {
                    if(filterSearch(info, patient)) {
                        System.out.printf("%-13s %-10s %-10s %-11s %-9d %-8s %-15s %-12s %-10s\n",
                            patient.getUID(), patient.getLastName(), patient.getFirstName(), 
                            patient.getMiddleName(), patient.getBirthday(), patient.getGender(),
                            patient.getAddress(), patient.getPhoneNum(), patient.getNationalID()
                        );
                    }
                }

                // ask user to input UID of the patient record to update
                System.out.print("\nEnter the patient's UID that you want to update: ");
                info = input.nextLine();
                found = 1;

            } 
            
            // show search result when exactly one patient is found
            if (found == 1) { 

                for (Patient patient : patientRecords) {
                    if (filterSearch(info, patient)) {
                        // display patient record to be edited
                        System.out.println(
                            patient.getUID() + "\n" +
                            patient.getLastName() + ", " + patient.getFirstName() + " " + 
                            patient.getMiddleName() + "\n" + patient.getBirthday() + "\n" +
                            patient.getAddress() + "\n" + patient.getPhoneNum() + "\n" + 
                            patient.getNationalID() + "\n"
                        );

                        // ask user which patient data they want to update in Patients.txt
                        System.out.println("Choose data to update:\n[1] Address\n[2] Phone Number");
                        int response = checkInput(1, 2);

                        // ask user to input new data for updating
                        System.out.print("Enter updated info: ");
                        String newInfo = input.nextLine();
                        
                        switch (response) {
                            case 1: patient.setAddress(newInfo); break;
                            case 2: patient.setPhoneNum(newInfo); break;
                        }
                                                
                        // indicate that the patient information in Patients.txt is updated
                        System.out.printf("The Address/Phone Number of patient %s has been updated.\n", patient.getUID());
                        
                    }
                }

                // if the user inputs 'Y' the loop will repeat
                System.out.print("Do you want to edit another patient record? [Y/N]: ");
                answer = input.nextLine();

            } else { // if(found==0) patient is not found, ask user if they want to search again 
                System.out.println("No record found.");
                System.out.print("\nSearch again? [Y/N] ");
                answer = checkAnswer();
            }
            
        } while ("Y".equalsIgnoreCase(answer));

    }

    // Delete Patient
    public static void deletePatient() {
        clear();
        
        String answer; // user's input to [Y/N] prompt
        String info;   // info of patient   
        int found;     // counter for number of results
            
        do {
            // reset answer and found variable at start of loop
            answer = "N";
            found = 0;

            // input for searching patient in the records
            System.out.print("\nEnter Patient Information: ");
            info = input.nextLine();

            // loops through the records to look for patient
            for (Patient patient : patientRecords) {
                if (filterSearch(info, patient)) found++; // counts number of patient's with the same data as input     
            }

            // if multiple patients detected, outputs row if there is no header.
            if (found > 1) {
                // header row
                System.out.printf("%-13s %-10s %-10s %-11s %-9s %-8s %-15s %-12s %-10s\n",
                    "Patient's UID", "Last Name", "First Name", "Middle Name",
                    "Birthday", "Gender", "Address", "Phone Number", "National ID no."
                );

                // display list of multiple patients
                for (Patient patient : patientRecords) {
                    if(filterSearch(info, patient)) {
                        System.out.printf("%-13s %-10s %-10s %-11s %-9d %-8s %-15s %-12s %-10s\n",
                            patient.getUID(), patient.getLastName(), patient.getFirstName(), 
                            patient.getMiddleName(), patient.getBirthday(), patient.getGender(),
                            patient.getAddress(), patient.getPhoneNum(), patient.getNationalID()
                        );
                    }
                }

                // ask user to input UID of the patient record to delete
                System.out.print("\nEnter the patient's UID that you want to delete: ");
                info = input.nextLine();
                found = 1;

            } 
            
            // show search result when exactly one patient is found
            if (found == 1) { 

                for (Patient patient : patientRecords) {
                    if (filterSearch(info, patient)) {
                        // display patient record to be deleted
                        System.out.println(
                            patient.getUID() + "\n" +
                            patient.getLastName() + ", " + patient.getFirstName() + " " + 
                            patient.getMiddleName() + "\n" + patient.getBirthday() + "\n" +
                            patient.getAddress() + "\n" + patient.getPhoneNum() + "\n" + 
                            patient.getNationalID() + "\n"
                        );

                        // ask user to confirm to delete patient
                        System.out.print("Delete Patient? [Y/N]: ");
                        String response = checkAnswer();

                        // indicate that the patient record is deleted in Patients.txt file
                        if("Y".equalsIgnoreCase(response)) {

                            // ask user to give reason for deleting the patient record
                            System.out.print("Reason: ");
                            String reason = input.nextLine();

                            patient.setIsDeleted(true);
                            patient.setReason(reason);

                            // for debugging
                            // System.out.println(patient.getIsDeleted());
                            // System.out.println(patient.getReason());

                            // indicate that the patient is deleted from Patients.txt
                            System.out.printf("Data of patient %s has been deleted.\n", patient.getUID());
                        }
                    }
                }

                // if the user inputs 'Y' the loop will repeat
                System.out.print("Do you want to delete another patient record? [Y/N]: ");
                answer = input.nextLine();

            } else { // if(found==0) patient is not found, ask user if they want to search again 
                System.out.println("No record found.");
                System.out.print("\nSearch again? [Y/N] ");
                answer = checkAnswer();
            }
            
        } while ("Y".equalsIgnoreCase(answer));
        
    }

    // Search Patient Record:
    public static void searchPatient() throws MalformedURLException, IOException, DocumentException {
        clear();
        
        String answer; // user's input to prompt
        int found = 0; // counter for number of results

        do{
            answer = "N";
            found = 0;

            // input for searching patient in the records
            System.out.print("\nEnter Patient Information: ");
            answer = input.nextLine();
    
            // loops through the records to look for patient
            for (Patient patient : patientRecords) {
                // counts number of patient's with the same data as input 
                if(filterSearch(answer, patient)) found++;     
            }

            // outputs row if there is no header and if there are patient's with same data
            if(found > 1) {
                System.out.printf("%-13s %-10s %-10s %-11s %-9s %-8s %-15s %-12s %-10s\n",
                    "Patient's UID", "Last Name", "First Name", "Middle Name",
                    "Birthday", "Gender", "Address", "Phone Number", "National ID no."
                );

                // display list of multiple patients
                for (Patient patient : patientRecords) {
                    if(filterSearch(answer, patient)) {
                        System.out.printf("%-13s %-10s %-10s %-11s %-9d %-8s %-15s %-12s %-10s\n",
                            patient.getUID(), patient.getLastName(), patient.getFirstName(), 
                            patient.getMiddleName(), patient.getBirthday(), patient.getGender(),
                            patient.getAddress(), patient.getPhoneNum(), patient.getNationalID()
                        );
                    }
                }

                System.out.print("\nEnter the patient's UID that you want to display: ");
                answer = input.nextLine();
                found = 1;
            }

            // search result is exactly one patient
            if(found == 1) { 
                for (Patient patient : patientRecords) {
                    if(filterSearch(answer, patient)) {
                        System.out.println(
                            patient.getUID() + "\n" +
                            patient.getLastName() + ", " + patient.getFirstName() + " " + 
                            patient.getMiddleName() + "\n" + patient.getBirthday() + "\n" +
                            patient.getAddress() + "\n" + patient.getPhoneNum() + "\n" + 
                            patient.getNationalID() + "\n"
                        );

                        System.out.printf("%-13s %-13s %-12s %-15s\n",
                            "Request's UID", "Lab Test Type", "Request Date", "Result"
                        );

                        // asks user if they want to print laboratory test results
                        System.out.print("Do you want to print a laboratory test result? [Y/N]: ");
                        answer = checkAnswer();

                        if("Y".equalsIgnoreCase(answer)) printPDF(patient);

                        answer = "N";
                    }
                }

            } else { // if(found==0) patient is not found, ask user if they want to search again
                System.out.println("No record found.");
                System.out.print("\nSearch again? [Y/N] ");
                answer = checkAnswer();
            }
            
        } while ("Y".equalsIgnoreCase(answer)); // loops again if user answers "Y"

    }

    /*/
     *  Creates UID for the patient
     *  Aian ( 05/16/22 ): Optimized the code
     *  Aian ( 05/17/22 ): Made a unique function name (pUID)
    /*/
    public static String generatepUID(int patient_num) {
        String retval;

        LocalDate date = LocalDate.now();
        
        retval = String.format("P%04d%02d%s", date.getYear(), date.getMonthValue(), formatValue(patient_num, 2, 3));
        
        return retval;
    }
    
    /*/
     *  Aian ( 05/16/22 ): Unused function?
    /*/
    public static void wordLength() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter word: ");
        String word = input.nextLine();
        
        System.out.println(word.length());
    
        input.close();
    }

/********************************************************************************************************************************************/

    /*/
     *  Service System
    /*/

    // Manage Service Records
    public static void manageService() throws MalformedURLException, IOException, DocumentException {
        clear();
        
        int transaction;
        String answer; // user's input to prompt
        String info;   // info of services
        int found;     // counts successful search
        boolean error; // indicates if there is an error

        System.out.print (
            "Manage Service Records\n" +
            "[1] Add New Service\n" +
            "[2] Edit Service Record\n" +
            "[3] Delete Service Record\n" +
            "[4] Search Service Record\n" +
            "[0] Return to Main Menu\n\n" +
            "Select a transaction: "
        );

        transaction = checkInput(0, 4);

        // Dom (05/20/22): removed switch case and combined the transactions to a single search algorithm
        // switch (transaction) {
        //     case 1: addService(); break;
        //     case 2: editService(); break;
        //     case 3: deleteService(); break;
        //     case 4: searchService(); break;
        // }

        clear();

        /*/ 
         *  Dom (05/20/22): Singular Search Algorithm for all transactions:
        /*/

        // Add New Service transaction
        if (transaction == 1) {
            do {
                // reset answer at start of loop
                answer = "N";

                addService();

                // ask if user wants to add another service
                System.out.print("Do you want to add another service? [Y/N]: ");
                answer = checkAnswer();

            } while ("Y".equalsIgnoreCase(answer));  

        } else if (transaction > 1 ) { // transaction is Edit, Delete, Search
            // if transaction is Edit Service:
            if (transaction == 2) {
                System.out.print(
                    "The services cannot be edited. If you would like to edit an existing service," +
                    "the service will first be deleted, and new service will be created.\nWould you like to proceed? [Y/N]: "
                );
                answer = checkAnswer();
            } else answer = "Y"; // transaction is Delete or Search

            // user agrees to process
            if ("Y".equalsIgnoreCase(answer)) {
                do {
                    // reset variable at start of loop
                    answer = "N";
                    found = 0;
                    error = false;

                    try {
                        // Search Service transaction:

                        // input for searching the service in serviceRecord ArrayList
                        System.out.print("\nEnter Service Information: ");
                        info = input.nextLine();
                
                        // sorts serviceRecords ArrayList alphabetically based on serviceCode
                        Collections.sort(serviceRecords, Comparator.comparing(Service::getServiceCode));

                        for (Service service : serviceRecords) {
                            if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                                // count successful search
                                found++;

                                // header
                                if(found == 1) System.out.printf("%-12s %-30s %s\n", "Service Code", "Description", "Price");
                                
                                // list
                                System.out.printf("%-12s %-30s %.2f\n", service.getServiceCode(), service.getDescription(), service.getPrice());
                            }

                        }

                        // Delete Service / Edit Service transaction:

                        // one search result
                        if (found == 1 && transaction != 4) {
                            for (Service service : serviceRecords) {
                                if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                                    // set isDeleted attribute = true to indicate that service object is deleted
                                    service.setIsDeleted(true);

                                    // ask user for reason for deletion, set Reason attribute to the user input
                                    System.out.print("Reason for deletion: ");
                                    service.setReason(input.nextLine());
                                }
                            }

                            // execute addService if transaction is Edit Service
                            if (transaction == 2) addService();

                        } else if (found > 1 && transaction != 4) { // multiple search results
                            // ask user to input service code to delete service
                            System.out.print("Enter service code of the service to delete: ");
                            info = input.nextLine();

                            for (Service service : serviceRecords) {
                                if (info.equalsIgnoreCase(service.getServiceCode())) {
                                    // set isDeleted attribute = true to indicate that service object is deleted
                                    service.setIsDeleted(true);

                                    // ask user for reason for deletion, set Reason attribute to the user input
                                    System.out.print("Reason for deletion: ");
                                    service.setReason(input.nextLine());
                                }
                            }

                            // execute addService if transaction is Edit Service
                            if (transaction == 2) addService();

                        } 
                        
                        if (found== 0) { // (found == 0) search unsuccessful
                            System.out.println("No record found.");
                            System.out.print("\nSearch again? [Y/N] ");
                            answer = checkAnswer();
                        }

                        switch (transaction) {
                            //transaction is Edit Service
                            case 2: System.out.print("Do you want to edit another service? [Y/N]: "); answer = checkAnswer(); break;

                            // transaction is Delete Service
                            case 3: System.out.print("Do you want to delete another service? [Y/N]: "); answer = checkAnswer(); break;
                        }

                    } catch (Exception e) {
                        error = true;
                        System.out.println("Error occured.");
                        e.printStackTrace();
                    }

                } while ("Y".equalsIgnoreCase(answer) || error == true); 
                // repeats process if user wants to edit another service or if system encounters an error
                
            } 

        }

    }

     // Add New Service
     public static void addService() {      
        clear();

        try {
            
            // 3 character code for the service
            System.out.print("Enter 3 character code: ");
            String serviceCode = input.nextLine();

            // description of service
            System.out.print("Enter description: ");
            String description = input.nextLine();

            // price of service
            System.out.print("Enter price: ");
            float price = input.nextFloat();

            // create Service object and store it in serviceRecords ArrayList
            serviceRecords.add(new Service(serviceCode, description, price, false, ""));

            System.out.println(serviceCode + " " + description + " has been added.\n");           

        } catch (Exception e) {
            System.out.println("Adding patient... Error occured.");
            e.printStackTrace();
        }
        
    } 

    // Search Service Record:
    public static void searchService() throws MalformedURLException, IOException, DocumentException {
        clear();
        
        String answer; // user's input to prompt
        String info;   // info of services
        int found;     // counts successful search

        do{
            // reset variable at start of loop
            answer = "N";
            found = 0;

            // input for searching the service in serviceRecord ArrayList
            System.out.print("\nEnter Service Information: ");
            info = input.nextLine();
    
            // sorts serviceRecords ArrayList alphabetically based on serviceCode
            Collections.sort(serviceRecords, Comparator.comparing(Service::getServiceCode));

            for (Service service : serviceRecords) {
                if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                    // count successful search
                    found++;

                    // header
                    if(found == 1) System.out.printf("%-12s %-30s %s\n", "Service Code", "Description", "Price");
                    
                    // list
                    System.out.printf("%-12s %-30s %.2f\n", service.getServiceCode(), service.getDescription(), service.getPrice());
                }

            }

            // search unsuccessful
            if (found == 0) { 
                System.out.println("No record found.");
                System.out.print("\nSearch again? [Y/N] ");
                answer = checkAnswer();
            }

        } while ("Y".equalsIgnoreCase(answer));

    }

    // Delete a Service
    public static void deleteService() {
        clear();
        
        String answer; // user's input to prompt
        String info;   // info of services
        int found;     // counts successful search

        do{
            // reset variable at start of loop
            answer = "N";
            found = 0;

            // input for searching the service in serviceRecord ArrayList
            System.out.print("\nEnter Service Information: ");
            info = input.nextLine();
    
            // sorts serviceRecords ArrayList alphabetically based on serviceCode
            Collections.sort(serviceRecords, Comparator.comparing(Service::getServiceCode));

            for (Service service : serviceRecords) {
                if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                    // count successful search
                    found++;

                    // header
                    if(found == 1) System.out.printf("%-12s %-30s %s\n", "Service Code", "Description", "Price");
                    
                    // list
                    System.out.printf("%-12s %-30s %.2f\n", service.getServiceCode(), service.getDescription(), service.getPrice());
                }

            }

            // one search result
            if (found == 1) {
                for (Service service : serviceRecords) {
                    if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                        // set isDeleted attribute = true to indicate that service object is deleted
                        service.setIsDeleted(true);

                        // ask user for reason for deletion, set Reason attribute to the user input
                        System.out.print("Reason for deletion: ");
                        service.setReason(input.nextLine());
                    }
                }

            } else if (found > 1) { // multiple search results
                // ask user to input service code to delete service
                System.out.print("Enter service code of the service to delete: ");
                info = input.nextLine();

                for (Service service : serviceRecords) {
                    if (info.equalsIgnoreCase(service.getServiceCode())) {
                        // set isDeleted attribute = true to indicate that service object is deleted
                        service.setIsDeleted(true);

                        // ask user for reason for deletion, set Reason attribute to the user input
                        System.out.print("Reason for deletion: ");
                        service.setReason(input.nextLine());
                    }
                }

            } else { // (found == 0) search unsuccessful
                System.out.println("No record found.");
                System.out.print("\nSearch again? [Y/N] ");
                answer = checkAnswer();
            }

        } while ("Y".equalsIgnoreCase(answer));

    }

    // Edit a Service
    public static void editService() {
        clear();
        
        String answer; // user's input to prompt
        String info;   // info of services
        int found;     // counts successful search
        Boolean error; // indicates if there is an error

        System.out.print(
            "The services cannot be edited. If you would like to edit an existing service," +
            "the service will first be deleted, and new service will be created.\nWould you like to proceed? [Y/N]: "
        );
        answer = checkAnswer();

        // user agrees to process
        if ("Y".equalsIgnoreCase(answer)) {
            do {
                // reset variable at start of loop
                answer = "N";
                found = 0;
                error = false;

                try {
                    // input for searching the service in serviceRecord ArrayList
                    System.out.print("\nEnter Service Information: ");
                    info = input.nextLine();
            
                    // sorts serviceRecords ArrayList alphabetically based on serviceCode
                    Collections.sort(serviceRecords, Comparator.comparing(Service::getServiceCode));

                    for (Service service : serviceRecords) {
                        if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                            // count successful search
                            found++;

                            // header
                            if(found == 1) System.out.printf("%-12s %-30s %s\n", "Service Code", "Description", "Price");
                            
                            // list
                            System.out.printf("%-12s %-30s %.2f\n", service.getServiceCode(), service.getDescription(), service.getPrice());
                        }

                    }

                    // one search result
                    if (found == 1) {
                        for (Service service : serviceRecords) {
                            if (info.equalsIgnoreCase(service.getServiceCode()) || service.getDescription().contains(info)) {
                                // set isDeleted attribute = true to indicate that service object is deleted
                                service.setIsDeleted(true);

                                // ask user for reason for deletion, set Reason attribute to the user input
                                System.out.print("Reason for deletion: ");
                                service.setReason(input.nextLine());
                            }
                        }

                        addService();

                    } else if (found > 1) { // multiple search results
                        // ask user to input service code to delete service
                        System.out.print("Enter service code of the service to delete: ");
                        info = input.nextLine();

                        for (Service service : serviceRecords) {
                            if (info.equalsIgnoreCase(service.getServiceCode())) {
                                // set isDeleted attribute = true to indicate that service object is deleted
                                service.setIsDeleted(true);

                                // ask user for reason for deletion, set Reason attribute to the user input
                                System.out.print("Reason for deletion: ");
                                service.setReason(input.nextLine());
                            }
                        }

                        addService();

                    } else { // (found == 0) search unsuccessful
                        System.out.println("No record found.");
                        System.out.print("\nSearch again? [Y/N] ");
                        answer = checkAnswer();
                    }

                    // ask user if they want to repeat transaction
                    System.out.print("Do you want to edit another service? [Y/N]: ");
                    answer = checkAnswer();

                } catch (Exception e) {
                    error = true;
                    System.out.println("Editing service... Error occured.");
                    e.printStackTrace();
                }

            } while ("Y".equalsIgnoreCase(answer) || error == true); 
            // repeats process if user wants to edit another service or if system encounters an error

        }

    }

/********************************************************************************************************************************************/

    /*/
     *  Lab Results System
    /*/

    // Manage Patient Records
    public static void manageRequests() throws MalformedURLException, IOException, DocumentException {
        clear();
        
        int answer;

        System.out.print (
            "Manage Laboratory Requests\n" +
            "[1] Add New Request\n" +
            "[2] Search Request\n" +
            "[3] Delete Request\n" +
            "[4] Edit Request\n" +
            "[0] Return to Main Menu\n\n" +
            "Select a transaction: "
        );

        answer = checkInput(0, 4);

        switch(answer) {
            case 0: mainMenu(); break;
            case 1: 
                addRequest();
            break;
            case 2: 
                searchRequest();
            break;
            case 3: 
                deleteRequest(); 
            break;
            case 4: 
                editRequest(); 
            break;
        }
    }

    public static void addRequest()
    {
        do
        {
            clear();

            Service service = returnService();

            // If User stops searching go back to menu
            if ( service == null ) return;

            // Stores data in array list
            readRequest( service.getServiceCode() );

            // Some Variables
            LabResults lbr = new LabResults();
            int requests_num = requestRecords.size() - 1;
            Patient patient = new Patient();
            int day = LocalDate.now().getDayOfMonth();

            System.out.println("");

            // If it is the first day of the month, reset request_num to 0 (AA00)
            // Else, increase the patient_num
            if(day == 1) requests_num = 0;
            else requests_num++;

            String rUID = generaterUID( requests_num, service.getServiceCode() );

            System.out.println("Enter Information for Request:");

            // Select Patient Prompt for request
            patient = returnPatient();

            // If User stops searching go back to menu
            if ( patient == null ) return;

            // Set Up LBR
            // rUID
            lbr.setrUID(rUID);

            // SetUP UID of Patient
            lbr.setpUID(patient.getUID());

            // Asks for Result
            System.out.println("Enter Lab Results:");
            lbr.setResults(input.nextLine());

            // Setup time is here for more accurate time requested
            lbr.reqDate = LocalDate.now().format( DateTimeFormatter.ofPattern("YYYYMMdd") );
            lbr.reqTime = ZonedDateTime.now(ZoneId.systemDefault()).format( DateTimeFormatter.ofPattern("HHmm") );

            System.out.printf("%s;%s;%s;%s;%s\n", lbr.getrUID(), lbr.getpUID(), lbr.getReqDate(), lbr.getReqTime(), lbr.getResults() );

            System.out.print("Confirm Request [Y/N]? ");
            if ( checkAnswer().equalsIgnoreCase("y") )
            {
                clear();
                requestRecords.add(lbr);
                writeRequest( service.getServiceCode() );
                System.out.print( "Laboratory Request " + lbr.rUID + " has been added to file " + service.getServiceCode() +"_Requests.txt.");
                loading(3);
            }
            else
            {
                clear();
                System.out.print("Request not added");
                loading(3);
            }

            // Flushes records for other sessions
            requestRecords.clear();
            
            clear();
            System.out.println( "Do you want to add another Laboratory Request? [Y/N]" );
        }
        while ( !checkAnswer().equalsIgnoreCase("N") );
    }

    public static String generaterUID( int requests_num, String serviceCode )
    {
        // Unique Alpha Numeric Identifier
        String id = formatValue( requests_num, 2, 2 );

        return serviceCode + LocalDate.now().format( DateTimeFormatter.ofPattern("YYYYMMdd")) + id;
    }

    // Writes on all files
    public static void writeRequest()
    {

        try 
        {

            for ( Service service : serviceRecords )
            {

                File outputFile = new File( service.getServiceCode().toUpperCase() + "_requests.txt" );

                if ( !outputFile.exists() )
                {
                    outputFile.createNewFile();
                }

                BufferedWriter writer = new BufferedWriter( new FileWriter( outputFile ) );

                for ( LabResults labResults : requestRecords )
                {
                    if ( labResults.getServiceCode() == null )
                    {
                        labResults.setServiceCode(labResults.getrUID().substring(0, 3));
                    }

                    // If not deleted
                    if ( labResults.getIsDeleted() == null || labResults.getIsDeleted() == false )
                    {
                        writer.write(
                            labResults.getrUID() + ";" +
                            labResults.getpUID() + ";" + 
                            labResults.getReqDate() + ";" + 
                            labResults.getReqTime() + ";" + 
                            labResults.getResults() + "\n" 
                        );
                    }
                    else 
                    {
                        writer.write(
                            labResults.getrUID() + ";" +
                            labResults.getpUID() + ";" + 
                            labResults.getReqDate() + ";" + 
                            labResults.getReqTime() + ";" + 
                            labResults.getResults() + ";" + 
                            "D" + ";" +
                            labResults.getDelReason() + "\n" 
                        );
                    }
                }
                
                writer.close();

            }
        }
        catch ( IOException e )
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    }

    // Writes on one file
    public static void writeRequest( String serviceCode )
    {

        try 
        {
            File outputFile = new File( serviceCode.toUpperCase() + "_requests.txt" );

            if ( !outputFile.exists() )
            {
                outputFile.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter( new FileWriter( outputFile ) );

            for ( LabResults labResults : requestRecords )
            {
                if ( labResults.getServiceCode() == null )
                {
                    labResults.setServiceCode(labResults.getrUID().substring(0, 3));
                }

                // If not deleted
                if ( labResults.getIsDeleted() == null || labResults.getIsDeleted() == false )
                {
                    writer.write(
                        labResults.getrUID() + ";" +
                        labResults.getpUID() + ";" + 
                        labResults.getReqDate() + ";" + 
                        labResults.getReqTime() + ";" + 
                        labResults.getResults() + "\n" 
                    );
                }
                else 
                {
                    writer.write(
                        labResults.getrUID() + ";" +
                        labResults.getpUID() + ";" + 
                        labResults.getReqDate() + ";" + 
                        labResults.getReqTime() + ";" + 
                        labResults.getResults() + ";" + 
                        "D" + ";" +
                        labResults.getDelReason() + "\n" 
                    );
                }
            }
            
            writer.close();
        }
        catch ( IOException e )
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }

    }

    // Reads a all request files from service code
    public static void readRequest()
    {
        try 
        {

            for ( Service service : serviceRecords )
            {

                File outputFile = new File( service.getServiceCode().toUpperCase() + "_requests.txt" );

                if ( !outputFile.exists() )
                {
                    outputFile.createNewFile();
                }

                Scanner scanner = new Scanner( outputFile );
                String current;
                String parts[] = {};

                while ( scanner.hasNextLine() )
                { 
                    current = scanner.nextLine();
                    parts = current.split(";");

                    if ( parts.length > 5 )
                    {
                        requestRecords.add( new LabResults( parts[0], parts[1], parts[2], parts[3], parts[4], true, parts[6], parts[0].substring(0, 3) ) );
                    }
                    else
                    {
                        requestRecords.add( new LabResults( parts[0], parts[1], parts[2], parts[3], parts[4], parts[0].substring(0, 3))  );
                    }

                }

                scanner.close();

            }

        }
        catch ( IOException e )
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    // Reads a single request file from service code
    public static void readRequest( String serviceCode )
    {
        try 
        {
            File outputFile = new File( serviceCode.toUpperCase() + "_requests.txt" );

            if ( !outputFile.exists() )
            {
                outputFile.createNewFile();
            }

            Scanner scanner = new Scanner( outputFile );
            String current;
            String parts[] = {};

            while ( scanner.hasNextLine() )
            { 
                current = scanner.nextLine();
                parts = current.split(";");

                if ( parts.length > 5 )
                {
                    requestRecords.add( new LabResults( parts[0], parts[1], parts[2], parts[3], parts[4], true, parts[6], parts[0].substring(0, 3) ) );
                }
                else
                {
                    requestRecords.add( new LabResults( parts[0], parts[1], parts[2], parts[3], parts[4], parts[0].substring(0, 3))  );
                }

            }

            scanner.close();

        }
        catch ( IOException e )
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    // Reusable Patient Search Engine
    // Returns Patient data type when patient with matching parameters are found
    // Its a loop until user stops
    public static Patient returnPatient() 
    {
        String answer; // user's input to prompt
        int found = 0; // counter for number of results

        do{

            answer = "N";
            found = 0;

            // input for searching patient in the records
            System.out.print("\nEnter Patient Information: ");
            answer = input.nextLine();
    
            // loops through the records to look for patient
            for (Patient patient : patientRecords) {
                // counts number of patient's with the same data as input 
                if(filterSearch(answer, patient)) found++;     
            }

            // Outputs row per row if there is no header and if there are patient's with same data
            if(found > 1) {

                System.out.printf("%-13s %-10s %-10s %-11s %-9s %-8s %-15s %-12s %-10s\n",
                    "Patient's UID", "Last Name", "First Name", "Middle Name",
                    "Birthday", "Gender", "Address", "Phone Number", "National ID no."
                );

                // Print all Matching Patients
                for (Patient patient : patientRecords) {
                    if(filterSearch(answer, patient)) {
                        System.out.printf("%-13s %-10s %-10s %-11s %-9d %-8s %-15s %-12s %-10s\n",
                            patient.getUID(), patient.getLastName(), patient.getFirstName(), 
                            patient.getMiddleName(), patient.getBirthday(), patient.getGender(),
                            patient.getAddress(), patient.getPhoneNum(), patient.getNationalID()
                        );
                    }
                }

                // User selects Patient that will be selected...
                System.out.print("\nEnter the patient's UID that you want to display: ");
                answer = input.nextLine();
                found = 1;

            }
            
            // search result is exactly one patient
            if(found == 1) 
            { 
                for (Patient patient : patientRecords) {
                    if(filterSearch(answer, patient)) {
                        
                        return patient;

                    }
                }
            } 
            else // if(found==0) patient is not found, ask user if they want to search again
            { 
                System.out.println("No record found.");
                System.out.print("\nSearch again? [Y/N] ");
                answer = checkAnswer();
            }
            
        } 
        while ("Y".equalsIgnoreCase(answer)); 
        // loops again if user answers "Y"

        // Return Null if User Cancels
        return null;

    }

    public static Service returnService()
    {

        do
        {
            System.out.println("Input Service Code:");
            String serviceCode = input.nextLine();

            // For each service obj in serviceRecords
            for ( Service service : serviceRecords )
            {
                // Check if serviceCode matches service.ServiceCode
                if ( service.getServiceCode().equalsIgnoreCase(serviceCode) )
                {
                    return service;
                }
            }
            System.out.println("Service Code does not exists,\nSearch again [Y/N]?");
        }
        while ( !checkAnswer().equalsIgnoreCase("N"));

        return null;

    }

    // non-case sensitive but requires exact code
    public static void searchRequest()
    {
        // Put all file inputs into requestRecords
        readRequest();

        do 
        {
            clear();

            ArrayList<LabResults> searchResults = new ArrayList<LabResults>();

            System.out.println("Input Request UID or Patient UID: ");
            String key = input.nextLine();
            String descriptor = "";

            // For each lbr in request records check if key matches request uid or patient uid
            for ( LabResults lbr : requestRecords )
            {
                if ( lbr.getrUID().equalsIgnoreCase( key ) || lbr.getpUID().equalsIgnoreCase( key ) )
                {
                    if ( lbr.getIsDeleted() == null )
                    searchResults.add(lbr);
                }
            }

            // Sorts searchResults
            Collections.sort( searchResults, Comparator.comparing( LabResults::getReqDate ).thenComparing( LabResults::getrUID ).reversed() );

            if ( searchResults.size() > 1 )
            {
                System.out.printf("%-15s | %-30s | %-12s | %s\n", "Request's UID", "Lab Test Type", "Request Date", "Result" );
                for ( LabResults lbr : searchResults )        
                {
                    for ( Service service : serviceRecords )
                    {
                        if ( service.getServiceCode().equalsIgnoreCase(lbr.getrUID().substring(0, 3)))
                        {
                            descriptor = service.getDescription();
                            break;
                        }
                    }
                    
                    if ( lbr.getIsDeleted() == false )
                    System.out.printf("%-15s | %-30s | %-12s | %s\n", lbr.getrUID(), descriptor, lbr.getReqDate(), lbr.getResults() );
                }
            }

            // If no matches
            if ( searchResults.size() == 0 )
            {
                System.out.print("No record found");
                loading(3);
            }
            System.out.println("Search again [Y/N]?");
        }
        while ( !checkAnswer().equalsIgnoreCase("N") );

    }

    public static ArrayList<LabResults> returnRequests( String pUID )
    {

        readRequest();

        ArrayList<LabResults> searchResults = new ArrayList<LabResults>();

        // For each lbr in request records check if key matches request uid or patient uid
        // I can probably turn this into a seperate function/method
        for ( LabResults lbr : requestRecords )
        {
            if ( lbr.getpUID().equalsIgnoreCase( pUID ) )
            {
                // Checks if entry is not deleted, adds if null
                if ( lbr.getIsDeleted() == null )
                searchResults.add(lbr);
            }
        }

        // Sorts searchResults
        Collections.sort( searchResults, Comparator.comparing( LabResults::getReqDate ).thenComparing( LabResults::getrUID ).reversed() );

        // Return null if empty
        if ( searchResults.isEmpty() )
        {
            return null;
        }
        else
        {
            return searchResults;
        }
    }

    public static void deleteRequest()
    {
        readRequest();

        do 
        {
            clear();

            ArrayList<LabResults> searchResults = new ArrayList<LabResults>();

            System.out.println("Input Request UID or Patient UID: ");
            String key = input.nextLine();
            String descriptor = "";

            // For each lbr in request records check if key matches request uid or patient uid
            for ( LabResults lbr : requestRecords )
            {
                if ( lbr.getrUID().equalsIgnoreCase( key ) || lbr.getpUID().equalsIgnoreCase( key ) )
                {
                    if ( lbr.getIsDeleted() == null )
                    searchResults.add(lbr);
                }
            }

            Collections.sort( searchResults, Comparator.comparing( LabResults::getReqDate ).thenComparing( LabResults::getrUID ).reversed() );

            // If there are more than 1 results
            if ( searchResults.size() > 1 )
            {
                // Print all results
                System.out.printf("%-15s | %-30s | %-12s | %s\n", "Request's UID", "Lab Test Type", "Request Date", "Result" );
                for ( LabResults lbr : searchResults )        
                {
                    for ( Service service : serviceRecords )
                    {
                        if ( service.getServiceCode().equalsIgnoreCase(lbr.getrUID().substring(0, 3)))
                        {
                            descriptor = service.getDescription();
                            break;
                        }
                    }
                    System.out.printf("%-15s | %-30s | %-12s | %s\n", lbr.getrUID(), descriptor, lbr.getReqDate(), lbr.getResults() );
                }

                // User inputs id of to be deleted
                System.out.println("\nInput UID of Request to be deleted: ");
                String answer = input.nextLine();
                // Removes all that does not match
                searchResults.removeIf( e -> !e.rUID.equalsIgnoreCase(answer) );
                
            }

            if ( searchResults.size() == 1 )
            {
                // Gets descriptor...
                for ( Service service : serviceRecords )
                {
                    if ( service.getServiceCode().equalsIgnoreCase(searchResults.get(0).getrUID().substring(0, 3)))
                    {
                        descriptor = service.getDescription();
                        break;
                    }
                }

                System.out.print("Entry Found");
                loading(3);

                System.out.printf("\n%-15s | %-30s | %-12s | %s\n", "Request's UID", "Lab Test Type", "Request Date", "Result" );
                System.out.printf("%-15s | %-30s | %-12s | %s\n", searchResults.get(0).getrUID(), descriptor, searchResults.get(0).getReqDate(), searchResults.get(0).getResults() );

                System.out.println("State reason for deletion:");
                searchResults.get(0).setDelReason(input.nextLine());
                searchResults.get(0).setIsDeleted(true);

                System.out.print("Overwritting file");

                // Clears current main arraylist then loads in file for selected service code
                requestRecords.clear();
                readRequest( searchResults.get(0).getServiceCode() );


                for ( LabResults lbr : requestRecords )
                {
                    if ( lbr.getrUID().equalsIgnoreCase(searchResults.get(0).getrUID()) )
                    {
                        lbr.setIsDeleted( searchResults.get(0).getIsDeleted() );
                        lbr.setDelReason( searchResults.get(0).getDelReason() );
                        // break is a failsafe...
                        break;
                    }
                }

                writeRequest( searchResults.get(0).getServiceCode() );
                loading(3);
                System.out.println("File Overwritten!");

            }
            else if ( searchResults.size() == 0 )
            {
                System.out.print("No record found");
                loading(3);
            }
            System.out.println("Do you want to delete another laboratory request? [Y/N]");
        }
        while ( !checkAnswer().equalsIgnoreCase("N") );

    }
    
    public static void editRequest()
    {

        readRequest();

        do 
        {
            clear();

            ArrayList<LabResults> searchResults = new ArrayList<LabResults>();

            System.out.println("Input Request UID or Patient UID: ");
            String key = input.nextLine();
            String descriptor = "";

            // For each lbr in request records check if key matches request uid or patient uid
            for ( LabResults lbr : requestRecords )
            {
                if ( lbr.getrUID().equalsIgnoreCase( key ) || lbr.getpUID().equalsIgnoreCase( key ) )
                {
                    if ( lbr.getIsDeleted() == null )
                    searchResults.add(lbr);
                }
            }

            Collections.sort( searchResults, Comparator.comparing( LabResults::getReqDate ).thenComparing( LabResults::getrUID ).reversed() );

            // If there are more than 1 results
            if ( searchResults.size() > 1 )
            {
                // Print all results
                System.out.printf("%-15s | %-30s | %-12s | %s\n", "Request's UID", "Lab Test Type", "Request Date", "Result" );
                for ( LabResults lbr : searchResults )        
                {
                    for ( Service service : serviceRecords )
                    {
                        if ( service.getServiceCode().equalsIgnoreCase(lbr.getrUID().substring(0, 3)))
                        {
                            descriptor = service.getDescription();
                            break;
                        }
                    }
                    System.out.printf("%-15s | %-30s | %-12s | %s\n", lbr.getrUID(), descriptor, lbr.getReqDate(), lbr.getResults() );
                }

                // User inputs id of to be deleted
                System.out.println("\nInput UID of Request to be deleted: ");
                String answer = input.nextLine();
                // Removes all that does not match
                searchResults.removeIf( e -> !e.rUID.equalsIgnoreCase(answer) );
                
            }

            if ( searchResults.size() == 1 )
            {
                // Gets descriptor...
                for ( Service service : serviceRecords )
                {
                    if ( service.getServiceCode().equalsIgnoreCase(searchResults.get(0).getrUID().substring(0, 3)))
                    {
                        descriptor = service.getDescription();
                        break;
                    }
                }

                System.out.print("Entry Found");
                loading(3);

                System.out.printf("\n%-15s | %-30s | %-12s | %s\n", "Request's UID", "Lab Test Type", "Request Date", "Result" );
                System.out.printf("%-15s | %-30s | %-12s | %s\n", searchResults.get(0).getrUID(), descriptor, searchResults.get(0).getReqDate(), searchResults.get(0).getResults() );

                System.out.println("Input new Result:");
                searchResults.get(0).setResults(input.nextLine());

                System.out.print("Overwritting file");

                // Clears current main arraylist then loads in file for selected service code
                requestRecords.clear();
                readRequest( searchResults.get(0).getServiceCode() );

                for ( LabResults lbr : requestRecords )
                {
                    if ( lbr.getrUID().equalsIgnoreCase(searchResults.get(0).getrUID()) )
                    {
                        lbr.setResults( searchResults.get(0).getResults());
                        // break is a failsafe...
                        break;
                    }
                }

                writeRequest( searchResults.get(0).getServiceCode() );
                loading(3);
                System.out.println("File Overwritten!");

            }
            else if ( searchResults.size() == 0 )
            {
                System.out.print("No record found");
                loading(3);
            }
            System.out.println("Do you want to edit another laboratory request? [Y/N]");
        }
        while ( !checkAnswer().equalsIgnoreCase("N") );
    }

}