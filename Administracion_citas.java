import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
//Martha Rdz
public class Administracion_citas {
    public static void main(String[] args) throws IOException {

        HashMap<String, Paciente> mapConPacientes = new HashMap<String, Paciente>();
        HashMap<String, Doctor> mapConDoctores = new HashMap<String, Doctor>();
        HashMap<String, Cita> mapConCitas = new HashMap<String, Cita>();
        Scanner teclado = new Scanner(System.in);

        //Carga de los archivos a leer
        loadDoctores(mapConDoctores);
        loadPacientes(mapConPacientes);
        loadCitas(mapConCitas);

        //Variables para agregar
        String idDoc ,nombreDoc, especialidadDoc;
        String idPac, nombrePac;
        String idCita, fechaCita, motivoCita ,nombreDocCita, nombrePacCita;

        String op;
        int  ban = 0;

        System.out.println("**Administración citas**");

        do {
            try{

                //Carga de datos de los archivos
                System.out.println("Selecciona la opción que deseas realizar");
                System.out.println("1.Alta de doctores");
                System.out.println("2.Consulta doctores");
                System.out.println("3.Alta pacientes");
                System.out.println("4.Consulta pacientes");
                System.out.println("5.Alta citas");
                System.out.println("6.Consulta citas");
                System.out.println("7.Salir");
                op = teclado.nextLine();

                switch (op) {
                    case "1":
                        System.out.println("\n*** Agregar Nuevo Doctor a la agenda");
                        System.out.println("\nIntroduzca el ID del doctor:");
                        idDoc = teclado.nextLine();
                        System.out.println("\nIntroduzca el nombre del doctor:");
                        nombreDoc = teclado.nextLine();
                        System.out.println("\nIntroduzca la especialidad del doctor:");
                        especialidadDoc = teclado.nextLine();

                        Doctor objDoctor = new Doctor();
                        objDoctor.nombreDoctor = nombreDoc;
                        objDoctor.especialidadDoctor = especialidadDoc;
                        createDoctores(mapConDoctores,idDoc,objDoctor);
                        saveDoctor(mapConDoctores);
                        break;

                    case "2":
                        listDoctor(mapConDoctores);
                        break;
                    case "3":
                        System.out.println("\n*** Agregar Nuevo Paciente a la agenda");
                        System.out.println("\nIntroduzca el ID del paciente:");
                        idPac = teclado.nextLine();
                        System.out.println("\nIntroduzca el nombre del paciente:");
                        nombrePac = teclado.nextLine();

                        Paciente objPaciente = new Paciente();
                        objPaciente.nombrePaciente = nombrePac;

                        createPaciente(mapConPacientes,idPac,objPaciente);
                        savePaciente(mapConPacientes);
                        break;

                    case "4":
                        listPaciente(mapConPacientes);
                        break;

                    case "5":
                        System.out.println("\n*** Agregar Nueva Cita a la agenda");
                        System.out.println("\nIntroduzca el ID de la cita:");
                        idCita = teclado.nextLine();
                        System.out.println("\nIntroduzca la fecha de la cita:");
                        fechaCita = teclado.nextLine();
                        System.out.println("\nIntroduzca el motivo de la cita:");
                        motivoCita = teclado.nextLine();

                        for(;;) {
                            System.out.println("\nIntroduzca el ID del Doctor");
                            nombreDocCita = teclado.nextLine();

                            //Si existe el doctor rompe ciclo

                             if (!mapConDoctores.containsKey(nombreDocCita)){
                                 System.out.println("\nError! ID no existente del doctor");

                                }
                             else {
                                 System.out.println("\nSelecciono el doctor:"+mapConDoctores.get(nombreDocCita).nombreDoctor+"\n " );
                                 break;
                             }
                        }  // Cierra for

                            for (;;){
                                System.out.println("\nIntroduzca el ID del paciente:");
                                nombrePacCita = teclado.nextLine();

                                if (!mapConPacientes.containsKey(nombrePacCita)){
                                    System.out.println("\nError! ID no existente del paciente");
                                }
                                else {
                                    System.out.println("\nSelecciono el Paciente:"+mapConPacientes.get(nombrePacCita).nombrePaciente+"\n " );
                                    break;
                                }
                            }

                        Cita objCita = new Cita();
                        objCita.fecha = fechaCita;
                        objCita.motivo = motivoCita;
                        objCita.doctorCita = nombreDocCita;
                        objCita.pacienteCita = nombrePacCita;

                        createCita(mapConCitas,idCita,objCita);
                        saveCita(mapConCitas);
                        break;

                    case "6":
                        listCita(mapConCitas);
                        break;

                    case "7":
                        System.out.println("Gracias por usar nuestra agenda");
                        System.exit(0);
                        break;
                }
            } catch (Exception e){
                System.out.println("Error");
                break;
            }

        }while (ban==0);

    }
    public static void loadDoctores(HashMap<String, Doctor> m) throws IOException {

        //Obtiene la ubicación actual del programa
        String currentPath = new java.io.File(".").getCanonicalPath();

        //Identifica si existe el directorio db
        String directoryName = currentPath  + "\\db";
        File directory = new File(String.valueOf(directoryName));

        //Crea directorio db y archivos csv
        if (!directory.exists()) {
            directory.mkdir();

            File filDoctores = new File(directoryName + "\\doctores.csv");
            filDoctores.createNewFile();
            File filPacientes = new File(directoryName + "\\pacientes.csv");
            filPacientes.createNewFile();
            File filCitas = new File(directoryName + "\\citas.csv");
            filCitas.createNewFile();
        }

        String inputFilenameDoctores = directoryName + "\\doctores.csv";

        String a [];

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader (new FileReader(inputFilenameDoctores));

            String line;
            while ((line=bufferedReader.readLine())!= null){
                a = line.split(",");

                Doctor objDoc = new Doctor();
                objDoc.nombreDoctor = a[1];
                objDoc.especialidadDoctor = a[2];

                m.put(a[0],objDoc);

            }
        } catch (IOException e){
            System.out.println("IOExpection catched while reading: "+e.getMessage());
        } finally {
            try{
                if (bufferedReader != null);
                bufferedReader.close();

            } catch (IOException e){
                System.out.println("IOException catched while closing"+e.getMessage());
            }
        }
    }//Termina función carga doctores

    public static void listDoctor(HashMap<String, Doctor> mapCon){
        Iterator<String> iterator = mapCon.keySet().iterator();

        System.out.println("Doctores: \n");
        System.out.println("ID\t|\tNombre\t|\tEspecialidad");
        System.out.println("---------------------------------");

        while (iterator.hasNext()){
            String llave = iterator.next();
            Doctor objDoc = mapCon.get(llave);

            System.out.println("   "+llave+"\t|\t"+objDoc.nombreDoctor + "\t|\t"+objDoc.especialidadDoctor);
        }
    }//Termina impresion Doctores

    public static void createDoctores (HashMap<String,Doctor> mapaDoc, String idDoc, Doctor objDoctor){

        if (mapaDoc.containsKey(idDoc)){
            System.out.println("\nError! ID existenete");
        }
        else {
            mapaDoc.put(idDoc, objDoctor);
            System.out.println("Doctor agregado");
        }
    } //Agregar Doctores

    public static void saveDoctor(HashMap<String,Doctor>m) throws IOException {

        Iterator<String> iterator = m.keySet().iterator();

        //Obtiene la ubicación actual del programa
        String currentPath = new java.io.File(".").getCanonicalPath();

        //Identifica si existe el directorio db
        String directoryName = currentPath  + "\\db";

        String inputFilenameDoctores = directoryName + "\\doctores.csv";

        BufferedWriter bufferedWriter = null;

        try{
            bufferedWriter = new BufferedWriter(new FileWriter(inputFilenameDoctores));

            while (iterator.hasNext()){
                String llave = iterator.next();
                Doctor objDoc = m.get(llave);
                bufferedWriter.write(llave+","+objDoc.nombreDoctor+","+objDoc.especialidadDoctor+"\n");
            }
            bufferedWriter.close();

        }catch (IOException e){
            System.out.println("IOException catched while writing: "+e.getMessage());
        }
    }//Guardado Doctores

    public static void loadPacientes(HashMap<String, Paciente> m) throws IOException {

        //Obtiene la ubicación actual del programa
        String currentPath = new java.io.File(".").getCanonicalPath();

        //Identifica si existe el directorio db
        String directoryName = currentPath  + "\\db";
        File directory = new File(String.valueOf(directoryName));

        String inputFilenamePacientes = directoryName + "\\pacientes.csv";

        String a [];

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader (new FileReader(inputFilenamePacientes));

            String line;
            while ((line=bufferedReader.readLine())!= null){
                a = line.split(",");

                Paciente objPaciente = new Paciente();
                objPaciente.nombrePaciente = a[1];

                m.put(a[0],objPaciente);
            }
        } catch (IOException e){
            System.out.println("IOExpection catched while reading: "+e.getMessage());
        } finally {
            try{
                if (bufferedReader != null);
                bufferedReader.close();

            } catch (IOException e){
                System.out.println("IOException catched while closing"+e.getMessage());
            }
        }
    }//Termina función carga pacientes

    public static void listPaciente(HashMap<String, Paciente> mapCon){
        Iterator<String> iterator = mapCon.keySet().iterator();

        System.out.println("Pacientes: \n");
        System.out.println("ID\t|\tNombre\t");
        System.out.println("---------------------------------");

        while (iterator.hasNext()){
            String llave = iterator.next();
            Paciente objPaciente = mapCon.get(llave);

            System.out.println("   "+llave+"\t|\t"+objPaciente.nombrePaciente);
        }
    }//Termina impresion Pacientes

    public static void createPaciente (HashMap<String,Paciente> mapaPac, String idPac, Paciente objPaciente){

        if (mapaPac.containsKey(idPac)){
            System.out.println("\nError! ID existenete");
        }
        else {
            mapaPac.put(idPac, objPaciente);
            System.out.println("Doctor agregado");
        }
    } //Agregar Pacientes

    public static void savePaciente(HashMap<String,Paciente>m) throws IOException {

        Iterator<String> iterator = m.keySet().iterator();

        //Obtiene la ubicación actual del programa
        String currentPath = new java.io.File(".").getCanonicalPath();

        //Identifica si existe el directorio db
        String directoryName = currentPath  + "\\db";

        String inputFilenameDoctores = directoryName + "\\pacientes.csv";

        BufferedWriter bufferedWriter = null;

        try{
            bufferedWriter = new BufferedWriter(new FileWriter(inputFilenameDoctores));

            while (iterator.hasNext()){
                String llave = iterator.next();
                Paciente objPac = m.get(llave);

                bufferedWriter.write(llave+","+objPac.nombrePaciente+"\n");
            }
            bufferedWriter.close();

        }catch (IOException e){
            System.out.println("IOException catched while writing: "+e.getMessage());
        }
    }//Guardado Pacientes


    public static void loadCitas(HashMap<String, Cita> m) throws IOException {

        //Obtiene la ubicación actual del programa
        String currentPath = new java.io.File(".").getCanonicalPath();

        //Identifica si existe el directorio db
        String directoryName = currentPath  + "\\db";
        File directory = new File(String.valueOf(directoryName));


        String inputFilenameCitas = directoryName + "\\citas.csv";

        String a [];

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader (new FileReader(inputFilenameCitas));
            String line;
            while ((line=bufferedReader.readLine())!= null){

                a = line.split(",");

                Cita objCita = new Cita();

                objCita.fecha = a[1];
                objCita.motivo = a[2];
                objCita.doctorCita = a[3];
                objCita.pacienteCita = a[4];

                m.put(a[0],objCita);

            }
        } catch (IOException e){
            System.out.println("IOExpection catched while reading: "+e.getMessage());
        } finally {
            try{
                if (bufferedReader != null);
                bufferedReader.close();

            } catch (IOException e){
                System.out.println("IOException catched while closing"+e.getMessage());
            }
        }
    }//Termina función carga citas

    public static void listCita(HashMap<String, Cita> mapCon){
        Iterator<String> iterator = mapCon.keySet().iterator();

        System.out.println("Cita: \n");
        System.out.println("ID\t|\tFecha\t|\tMotivo\t|\tDoctor\t|\tPaciente\t");
        System.out.println("---------------------------------------");

        while (iterator.hasNext()){
            String llave = iterator.next();
            Cita objCita = mapCon.get(llave);

            System.out.println("   "+llave+"\t|\t"+objCita.fecha+"\t|\t"+objCita.motivo
                    +"\t|\t"+objCita.doctorCita+"\t|\t"+objCita.pacienteCita
            );
        }
    }//Termina impresion Cita

    public static void createCita (HashMap<String,Cita> mapaCit, String idCita, Cita objCita){

        if (mapaCit.containsKey(idCita)){
            System.out.println("\nError! ID existenete");
        }
        else {
            mapaCit.put(idCita, objCita);
            System.out.println("Cita agregada");
        }
    } //Agregar Cita

    public static void saveCita(HashMap<String,Cita>m) throws IOException {

        Iterator<String> iterator = m.keySet().iterator();

        //Obtiene la ubicación actual del programa
        String currentPath = new java.io.File(".").getCanonicalPath();

        //Identifica si existe el directorio db
        String directoryName = currentPath  + "\\db";

        String inputFilenameDoctores = directoryName + "\\citas.csv";

        BufferedWriter bufferedWriter = null;

        try{
            bufferedWriter = new BufferedWriter(new FileWriter(inputFilenameDoctores));

            while (iterator.hasNext()){
                String llave = iterator.next();
                Cita objCita = m.get(llave);

                bufferedWriter.write(llave+","+objCita.fecha+","+objCita.motivo
                        +","+objCita.doctorCita+","+objCita.pacienteCita+"\n");
            }
            bufferedWriter.close();

        }catch (IOException e){
            System.out.println("IOException catched while writing: "+e.getMessage());
        }
    }//Guardado Citas
}