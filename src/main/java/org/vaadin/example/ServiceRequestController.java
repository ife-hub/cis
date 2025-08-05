package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vaadin.example.MSSQLTableReader;
import org.vaadin.example.ServiceRequestService;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = {"http://localhost:5050"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class ServiceRequestController {

    private final ServiceRequestService srmr;
    private Set<String> rqList = new HashSet<>();
    private Set<String> actionList = new HashSet<>();
    private List<Map<String, Object>> results;

    @Autowired
    public ServiceRequestController(ServiceRequestService srmr, MSSQLTableReader mtr){
        this.srmr = srmr;
        results = mtr.getAll();
    }

    @GetMapping("/rqTypes")
    public ResponseEntity<Set<String>> getAllRqTypes(){
        try{
            return new ResponseEntity<>(srmr.getRqTypes(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getActions/{rqType}")
    public ResponseEntity<Set<String>> getAllActions(@PathVariable String rqType){
        try{
            return new ResponseEntity<>(srmr.getActions(rqType), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStateFroms/{rqType}")
    public ResponseEntity<Set<String>> getStateFroms(@PathVariable String rqType){
        try{
            return new ResponseEntity<>(srmr.getStateFroms(rqType), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStateTos/{rqType}")
    public ResponseEntity<Set<String>> getStateTos(@PathVariable String rqType){
        try{
            return new ResponseEntity<>(srmr.getStateTos(rqType), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getStartingStates/{rqType}")
    public ResponseEntity<List<String>> getStartingStates(@PathVariable String rqType){
        try{
            return new ResponseEntity<>(srmr.getStartingStates(rqType), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCorActions/{rqType}/{stateFrom}")
    public ResponseEntity<Set<String>> getCorActions(@PathVariable String rqType, @PathVariable String stateFrom){
        try{
            return new ResponseEntity<>(srmr.getCorActions(rqType, stateFrom), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCorStateTo/{rqType}/{stateFrom}/{action}")
    public ResponseEntity<String> getCorActions(@PathVariable String rqType, @PathVariable String stateFrom, @PathVariable String action){
        try{
            return new ResponseEntity<>(srmr.getCorStateTo(rqType, stateFrom, action), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getIsTerminal/{rqType}/{stateTo}")
    public ResponseEntity<Boolean> getStateTos(@PathVariable String rqType, @PathVariable String stateTo){
        try{
            return new ResponseEntity<>(srmr.isTerminal(rqType, stateTo), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}