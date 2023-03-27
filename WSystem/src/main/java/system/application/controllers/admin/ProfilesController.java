package system.application.controllers.admin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import system.application.controllers.FxmlLoader;
import system.backend.WSystem;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

public class ProfilesController {
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Button viewOwnersButton = null;
    @FXML
    private Button viewAgentsButton = null;
    private WSystem wSystem = WSystem.getInstance();
    private BooleanProperty agentProfilesCheck = new SimpleBooleanProperty(false);
    private BooleanProperty ownerProfilesCheck = new SimpleBooleanProperty(false);

    public void initialize(){
        profilesState();
        viewOwnersButton.visibleProperty().bind(ownerProfilesCheck);
        viewAgentsButton.visibleProperty().bind(agentProfilesCheck);
    }
    public void profilesState(){
        if (wSystem.hasOwnerProfiles())
            ownerProfilesCheck.set(true);
        if (wSystem.hasAgentProfiles())
            agentProfilesCheck.set(true);
    }
    public void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);

    }

    public void handleViewOwners(ActionEvent event) {
        setLoader("viewOwnersFXML");
        System.out.println("Showing owners here:");

        for (Owner owner : wSystem.getOwners()) {
            System.out.println("\nOwner:");
            System.out.println(owner.getID());
            System.out.println(owner.getFirstname());
            System.out.println(owner.getLastname());
            System.out.println(owner.getUsername());
            System.out.println(owner.getPassword());
            System.out.println(owner.getEmailAddress());
            System.out.println(owner.getPhoneNumber());
        }
    }

    public void handleViewAgents(ActionEvent event) {
        setLoader("viewAgentsFXML");
            System.out.println("Showing agents here:");

            for (Agent agent : wSystem.getAgents()) {
                System.out.println("\nAgent:");
                System.out.println(agent.getID());
                System.out.println(agent.getFirstname());
                System.out.println(agent.getLastname());
                System.out.println(agent.getUsername());
                System.out.println(agent.getPassword());
                System.out.println(agent.getEmailAddress());
                System.out.println(agent.getPhoneNumber());
            }
    }

}
