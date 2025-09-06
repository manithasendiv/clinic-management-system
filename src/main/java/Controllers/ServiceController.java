package Controllers;

import Models.Patient;
import Models.Service;
import ServiceLayer.ServicesService;

public class ServiceController {
        Service serviceObj;

        public ServicesService service;

        public Service addNote(int serviceID,String note){
            serviceObj = new Service(note,serviceID);
            return serviceObj;
        }
        public ServiceController(){
            service= new ServicesService();
         }
        public boolean addNoteToDatabase() {
            return service.AddNote(serviceObj);
        }

}
