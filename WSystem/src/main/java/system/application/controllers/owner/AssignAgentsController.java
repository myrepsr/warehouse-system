package system.application.controllers.owner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import system.backend.WSystem;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.others.Warehouse;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssignAgentsController implements Initializable {
    @FXML
    private Button doneButton;
    @FXML
    private Label violationsLabel;
    @FXML
    private Label successLabel;
    @FXML
    private ListView<String> agentsView;    // listView of agents
    @FXML
    private ListView<String> warehousesView;    // listView of warehouses

    private ObservableList<String> agentsList = FXCollections.observableArrayList();    // ALL AGENTS
    private ObservableList<String> selectedAgentsList = FXCollections.observableArrayList();  // selected AGENTS
    private ObservableList<String> warehousesList = FXCollections.observableArrayList();    // populated with all warehouses of the owner
    private ObservableList<String> selectedWarehouses = FXCollections.observableArrayList();    // populated with selected warehouses
    private WSystem wSystem = WSystem.getInstance();
    private Owner owner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        agentsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enables multiple choice for the ListView
        warehousesView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enables multiple choice for the ListView

        OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();
        owner = ownerDataHolder.getOwner();

        if(wSystem.hasAgentProfiles()) {    // Fills the listViews with their respective values if there are AGENTS
            addAgentsToListView();
            outputWarehouses();
        }
        else
            System.out.println("The system doesn't have agent profiles!");
    }

    public void  addAgentsToListView(){ // adds agents to agentsView
        System.out.println("The system has agent profiles!");
        System.out.println("Showing agents here:");

        for (Agent agent : wSystem.getAgents()) {
            System.out.println(agent.getFirstname());
            agentsList.add(agent.getFirstname());
        }
        agentsView.setItems(agentsList);

    }

    public void outputWarehouses(){ // add warehouses to warehousesView
        List<Warehouse> warehouseList = owner.getWarehouses();
        if(!warehouseList.isEmpty()) {
            System.out.println("The owner has warehouses!");
            for(Warehouse wh : warehouseList){
                warehousesList.add(Long.toString(wh.getID()));

                System.out.println();
            }
            warehousesView.setItems(warehousesList);
        }
        else System.out.println("The owner doesn't have warehouses!");
    }


    public void handleDoneButton(ActionEvent event) {   // When done button is pressed
        violationsLabel.setVisible(false);
        successLabel.setVisible(false);

        System.out.println("You clicked: " + doneButton.getText());
        selectedAgentsList = agentsView.getSelectionModel().getSelectedItems(); // assigns selected elements to a list
        selectedWarehouses = warehousesView.getSelectionModel().getSelectedItems(); // assigns selected elements to a list

        if (!selectedAgentsList.isEmpty() && !selectedWarehouses.isEmpty()){  // checks if there are selected items in both listViews
            successLabel.setVisible(true);
            System.out.println(selectedAgentsList.toString());
            System.out.println(selectedWarehouses.toString());
            for (String o : selectedAgentsList)
                System.out.println("Agent = " + o);
        }else
            violationsLabel.setVisible(true);
    }
}
