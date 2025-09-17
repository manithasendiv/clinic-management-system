package Controllers;

import Models.Patient;
import Models.Service;
import ServiceLayer.ServicesService;

import java.io.File;

public class ServiceController {
        Service serviceObj;

        public ServicesService service;

        public Service addNote(int serviceID,String note){
            serviceObj = new Service(note,serviceID);
            return serviceObj;
        }

        public Service AddService(String name,String doctor,int pateintID){
            serviceObj = new Service(name,doctor,pateintID);
            return  serviceObj;
        }

        public ServiceController(){
            service= new ServicesService();
         }
        public boolean addNoteToDatabase() {
            return service.AddNote(serviceObj);
        }

        public boolean removeService(int sid) {
        return service.deleteService(sid);
    }

        //public boolean addFileToDatabase() {return  service.}
        public  boolean addServiceToDatabase(){return  service.addService(serviceObj);}

}
