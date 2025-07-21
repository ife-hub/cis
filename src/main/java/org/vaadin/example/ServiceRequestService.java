package org.vaadin.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.MSSQLTableReader;
import org.vaadin.example.repositories.ServiceRequestMapRepository;

import java.util.*;

@Service
public class ServiceRequestService {
    
    private final MSSQLTableReader mtr;
    private Set<String> rqList = new HashSet<>();
    private Set<String> actionList = new HashSet<>();
    private List<Map<String, Object>> results = new ArrayList<>();

    @Autowired
    public ServiceRequestService(MSSQLTableReader mtr){
        this.mtr = mtr;
        results = mtr.getAll();
    }

    public Set<String> getRqTypes(){

        int i = 1;
        for (Map<String, Object> map : results) {
            //System.out.println("Row " + i);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //System.out.println(entry.getKey() + " = " + entry.getValue());
                if (entry.getKey().equalsIgnoreCase("RQType")){
                    rqList.add((String) entry.getValue());
                }
                String rqS = (String) entry.getValue();
            }
            //System.out.println("---"); // separator between map entries
            i++;
        }
        for(String rq : rqList){
            //System.out.println(rq);
        }

        return rqList;
    }

    public Set<String> getActions(String rqType){
        int i = 1;
        List<Map<String, Object>> acts = new ArrayList<>();
        Set<String> actions = new HashSet<>();
        for (Map<String, Object> map : results) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String rqS = (String) entry.getValue();
                if (entry.getKey().equalsIgnoreCase("RQType") && rqS.equalsIgnoreCase(rqType)){
                    acts.add(map);
                }
            }
            i++;
        }
        for (Map<String, Object> map : acts) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("Action")){
                    if (entry.getValue() != null){
                        actions.add((String) entry.getValue());
                    }
                }
            }
        }
        for(String ac : actions){
            System.out.println("Action: " + ac);
        }
        return actions;
    }

    public Set<String> getStateFroms(String rqType){
        int i = 1;
        List<Map<String, Object>> fromss = new ArrayList<>();
        Set<String> froms = new HashSet<>();
        for (Map<String, Object> map : results) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String rqS = (String) entry.getValue();
                if (entry.getKey().equalsIgnoreCase("RQType") && rqS.equalsIgnoreCase(rqType)){
                    fromss.add(map);
                }
            }
            i++;
        }
        for (Map<String, Object> map : fromss) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateFrom")){
                    String fromString = (String) entry.getValue();
                    if (entry.getValue() != null && !fromString.equalsIgnoreCase("")){
                        froms.add(fromString);
                    }
                }
            }
        }
        for(String fr : froms){
            System.out.println("StateFrom: " + fr);
        }
        return froms;
    }

    public Set<String> getStateTos(String rqType){
        int i = 1;
        List<Map<String, Object>> to1 = new ArrayList<>();
        Set<String> to2 = new HashSet<>();
        for (Map<String, Object> map : results) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String rqS = (String) entry.getValue();
                if (entry.getKey().equalsIgnoreCase("RQType") && rqS.equalsIgnoreCase(rqType)){
                    to1.add(map);
                }
            }
            i++;
        }
        for (Map<String, Object> map : to1) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateTo")){
                    String toString = (String) entry.getValue();
                    if (entry.getValue() != null && !toString.equalsIgnoreCase("")){
                        to2.add(toString);
                    }
                }
            }
        }
        for(String to : to2){
            System.out.println("StateTo: " + to);
        }
        return to2;
    }

    public List<String> getStartingStates(String rqType){
        int i = 1;
        List<Map<String, Object>> to1 = new ArrayList<>();
        Set<String> froms = new HashSet<>();
        Set<String> tos = new HashSet<>();
        for (Map<String, Object> map : results) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String rqS = (String) entry.getValue();
                if (entry.getKey().equalsIgnoreCase("RQType") && rqS.equalsIgnoreCase(rqType)){
                    to1.add(map);
                }
            }
            i++;
        }
        for (Map<String, Object> map : to1) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateTo")){
                    String toString = (String) entry.getValue();
                    if (entry.getValue() != null && !toString.equalsIgnoreCase("")){
                        tos.add(toString);
                    }
                }
            }
        }
        for (Map<String, Object> map : to1) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateFrom")){
                    String fromString = (String) entry.getValue();
                    if (entry.getValue() != null && !fromString.equalsIgnoreCase("")){
                        froms.add(fromString);
                    }
                }
            }
        }
        List<String> result = new ArrayList<>(froms); // Copy listA to avoid modifying it
        result.removeAll(tos);
        return result;
    }

    public Set<String> getCorActions(String rqType, String stateFrom){
        int i = 1;
        List<Map<String, Object>> rqs = new ArrayList<>();
        List<Map<String, Object>> fromsss = new ArrayList<>();
        Set<String> actions = new HashSet<>();
        for (Map<String, Object> map : results) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String rqS = (String) entry.getValue();
                if (entry.getKey().equalsIgnoreCase("RQType") && rqS.equalsIgnoreCase(rqType)){
                    rqs.add(map);
                }
            }
            i++;
        }
        for (Map<String, Object> map : rqs) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateFrom")){
                    String fromString = (String) entry.getValue();
                    if (entry.getValue() != null && !fromString.equalsIgnoreCase("") && fromString.equalsIgnoreCase(stateFrom)){
                        fromsss.add(map);
                    }
                }
            }
        }
        for (Map<String, Object> map : fromsss) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("Action")){
                    String actionString = (String) entry.getValue();
                    if (entry.getValue() != null && !actionString.equalsIgnoreCase("")){
                        actions.add(actionString);
                    }
                }
            }
        }
        for(String ac : actions){
            System.out.println("CorActions: " + ac);
        }
        return actions;
    }

    public String getCorStateTo(String rqType, String stateFrom, String action){
        int i = 1;
        List<Map<String, Object>> rqs = new ArrayList<>();
        List<Map<String, Object>> fromsss = new ArrayList<>();
        List<Map<String, Object>> actions = new ArrayList<>();
        String stateTo = "";
        for (Map<String, Object> map : results) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String rqS = (String) entry.getValue();
                if (entry.getKey().equalsIgnoreCase("RQType") && rqS.equalsIgnoreCase(rqType)){
                    rqs.add(map);
                }
            }
            i++;
        }
        for (Map<String, Object> map : rqs) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateFrom")){
                    String fromString = (String) entry.getValue();
                    if (entry.getValue() != null && !fromString.equalsIgnoreCase("") && fromString.equalsIgnoreCase(stateFrom)){
                        fromsss.add(map);
                    }
                }
            }
        }
        for (Map<String, Object> map : fromsss) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("Action")){
                    String actionString = (String) entry.getValue();
                    if (entry.getValue() != null && !actionString.equalsIgnoreCase("") && actionString.equalsIgnoreCase(action)){
                        actions.add(map);
                    }
                }
            }
        }
        for (Map<String, Object> map : actions) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("StateTo")){
                    String stateToString = (String) entry.getValue();
                    if (entry.getValue() != null && !stateToString.equalsIgnoreCase("")){
                        stateTo = stateToString;
                    }
                }
            }
        }
        System.out.println("CorStateTo: " + stateTo);
        return stateTo;
    }

    public boolean isTerminal(String rqType, String stateString){
        Set<String> stateFroms = getStateFroms(rqType);

        boolean exists = !stateFroms.contains(stateString);

        return exists;
    }
}
