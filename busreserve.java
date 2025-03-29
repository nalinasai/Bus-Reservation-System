import java.io.*;
import java.util.*;

class invalidinputcode extends Exception{
    public invalidinputcode(String message){
        super(message);
    }
}

class invalidinputseats extends Exception{
    public invalidinputseats(String message){
        super(message);
    }
}

class Buses{
    String routecode;
    String busnumber;
    double cost_per_seat;
    int totalseats;
    int availableseats;
    String driver;
    String Ac_or_NonAc;
    String phonenumber;
    String busroute;

    Buses(String routecode,String busnumber,double cost_per_seat,int totalseats,int availableseats,String driver,String Ac_or_NonAc,String phonenumber,String busroute){
        this.routecode = routecode;
        this.busnumber = busnumber;
        this.cost_per_seat =cost_per_seat;
        this.totalseats = totalseats;
        this.availableseats = availableseats;
        this.driver = driver;
        this.Ac_or_NonAc = Ac_or_NonAc;
        this.phonenumber = phonenumber;
        this.busroute = busroute;
    }
}

class reserve{
    
    int inputseats;
    Buses valid_bus_code_selected;

    reserve(int inputseats,Buses valid_bus_code_selected){
        this.inputseats = inputseats;
        this.valid_bus_code_selected = valid_bus_code_selected;
    }

    public void update_the_seats(){
        String filename = "busdetails.csv";
        List<String> lines = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine();
            String line;
            while((line=br.readLine())!=null){
                String[] details = line.split(",");
                if(details[0].equalsIgnoreCase(valid_bus_code_selected.routecode && details[1].equalsIgnoreCase(valid_bus_code_selected.busnumber))){
                    details[4] = valid_bus_code_selected.availableseats - this.inputseats;
                }
                else{
                    continue;
                }
                line = String.join(",",details);
                lines.add(line);
            }

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))){
            bw.write("routecode,Bus Number,Cost Per Seat,Total Seats,Available Seats,Driver,AC/Non AC,Phone Number,Bus Route");
            bw.newLine();
            for(String line_by_line: lines){
                bw.write(line_by_line);
                bw.newLine();
            }
        }
        catch(IOException e){
            System.out.prinltn(e.getMessage());
        }

    }

}

public class busreserve{
    private static final String filename = "busdetails.csv";
    private static Map<String,List<Buses>> busitem = new HashMap<>();

    public static void main(String args[]){
        loadthedetails();
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Here are the Routecodes: ");
        System.out.println("Jaffna to Colombo --> 1: ");
        System.out.println("Colombo to Galle --> 2: ");
        System.out.println("Kandy to Nuwara Eliya --> 3: ");
        System.out.println("Batticaloa to Trincomalee --> 4: ");
        System.out.println("Matara to Hambantota--> 5: ");
        System.out.println();

        List<Buses> buses_same_routes;

        while(true){
            System.out.print("Enter the route code: ");
            String inputroutecode = scan.nextLine();
            try{
                if(busitem.containsKey(inputroutecode)){
                    buses_same_routes = busitem.get(inputroutecode);
                    break;
                }
                else{
                    throw new invalidinputcode("Wrong bus route code!! Enter correct bus route code: ");
                }

            }
            catch(invalidinputcode e){
                System.out.println(e.getMessage());
            }
        }

        List<Buses> selectedacornot = new ArrayList<>();
        String ac_or_not;
        while(true){
            System.out.print("Enter if AC; 1 OR Non-AC; 0: ");
            ac_or_not = scan.nextLine();
            selectedacornot.clear();
            
            boolean selectedacornot_found = false;

            for(int i=0; i<buses_same_routes.size(); i++){
                if(ac_or_not.equalsIgnoreCase(buses_same_routes.get(i).Ac_or_NonAc.trim())){
                    selectedacornot.add(buses_same_routes.get(i));
                    selectedacornot_found = true;
                }
                else{
                    continue;
                }
            }

            
            if(!selectedacornot_found){
                System.out.println("There is no available bus that you want.");
                
                
                while(true){
                    System.out.print("Do you want to try other type (AC / NON_AC) yes or no :");
                    String prefer = scan.nextLine();
                    if(prefer.equalsIgnoreCase("yes")){
                        break;
                    }
                    else if(prefer.equalsIgnoreCase("no")){
                        System.out.println("THANK YOU!!");
                        return;
                        
                    }
                    else{
                        System.out.println("Enter correct input:");
                        continue;
                    }
                }
            }
            else{
                break;
            }
            

        }

        
        for(int j=0; j<selectedacornot.size(); j++){
            System.out.println("Bus number: "+selectedacornot.get(j).busnumber+ " ,cost_per_seat: "+selectedacornot.get(j).cost_per_seat+" ,totalseats: "+selectedacornot.get(j).totalseats+" ,available seats: "+selectedacornot.get(j).availableseats+ " ,phone number: "+selectedacornot.get(j).phonenumber+" ,driver name: "+selectedacornot.get(j).driver);
        }
        
        Buses valid_bus_code_selected = null;
        boolean validbuscode = false;
        while(true){
            System.out.print("Enter the bus code you want: ");
            String preferbuscode = scan.nextLine();
            for(int k=0; k<selectedacornot.size(); k++){
                if(selectedacornot.get(k).busnumber.equalsIgnoreCase(preferbuscode)){
                    validbuscode = true;
                    valid_bus_code_selected = selectedacornot.get(k);
                    break;
                }
                else{
                    continue;
                }
            }

            if(!validbuscode){
                System.out.println("Your bus code is incorrect!!");
            }
            else{
                break;
            }

        }

        System.out.println("Here is your bus details: ");
        System.out.println("Bus number: "+valid_bus_code_selected.busnumber+ " ,cost_per_seat: "+valid_bus_code_selected.cost_per_seat+" ,totalseats: "+valid_bus_code_selected.totalseats+" ,available seats: "+valid_bus_code_selected.availableseats+ " ,phone number: "+valid_bus_code_selected.phonenumber+" ,driver name: "+valid_bus_code_selected.driver);
        System.out.println();

        boolean seatsavailable = false;
        int inputseats;
        while(true){
            System.out.print("How many seats you want? ");
            inputseats = scan.nextInt();
            scan.nextLine();
            try{
                if(inputseats<valid_bus_code_selected.availableseats){
                    System.out.print("Are you confirm your reservation? yes/no: ");
                    String confirmbooking = scan.nextLine();
                    if(confirmbooking.equalsIgnoreCase("yes")){
                        break;
                    }
                    else{
                        System.out.println("Thankyou so much. ");
                        return;
                    }
                }
                else{
                    throw new invalidinputseats("There is no available seats. You can try less than this numbe rof seats.");
                }
            }
            catch(invalidinputseats e){
                System.out.println(e.getMessage());
            }
        }
        
        //System.out.println(inputseats);
        reserve reserveseats = new reserve(inputseats,valid_bus_code_selected);
        reserveseats.update_the_seats();
        
        
    }


    private static void loadthedetails(){
        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine();
            while((line=br.readLine())!=null){
                String[] details = line.split(",");

                String routecode = details[0];
                String busnumber = details[1];
                double cost_per_seat = Double.parseDouble(details[2]);
                int totalseats = Integer.parseInt(details[3]);
                int availableseats = Integer.parseInt(details[4]);
                String driver = details[5];
                String Ac_or_NonAc = details[6];
                String phonenumber = details[7];
                String busroute = details[8];

                Buses bus = new Buses(routecode,busnumber,cost_per_seat,totalseats,availableseats,driver,Ac_or_NonAc,phonenumber,busroute);
                busitem.computeIfAbsent(routecode, k -> new ArrayList<>()).add(bus);

            }

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}