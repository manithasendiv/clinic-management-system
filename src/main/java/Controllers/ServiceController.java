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
        public Service addFile(int serviceID, File file){
            serviceObj = new Service(file,serviceID);
            return serviceObj;
        }
        public ServiceController(){
            service= new ServicesService();
         }
        public boolean addNoteToDatabase() {
            return service.AddNote(serviceObj);
        }
        //public boolean addFileToDatabase() {return  service.}

}
