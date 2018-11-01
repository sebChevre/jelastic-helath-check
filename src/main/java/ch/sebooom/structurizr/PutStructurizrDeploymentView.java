package ch.sebooom.structurizr;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.api.StructurizrClientException;
import com.structurizr.model.*;
import com.structurizr.view.DeploymentView;
import com.structurizr.view.Shape;
import com.structurizr.view.ViewSet;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;

public class PutStructurizrDeploymentView {

    private static final long WORKSPACE_ID = 41014;
    private static final String API_KEY = "a0d9b4c1-229e-470d-b6d4-cad5ccc6e03e ";
    private static final String API_SECRET = "6e12e01a-a5a2-4c63-989e-f79cf9338062";

    private static final String JMS_TAG = "jms";


    public static void main(String[] args) throws StructurizrClientException {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);


        Workspace workspace = structurizrClient.getWorkspace(WORKSPACE_ID);

        //Workspace workspace = new Workspace("HTTP-based health checks example", "An example of how to use the HTTP-based health checks feature");
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        SoftwareSystem structurizr = model.addSoftwareSystem("Structurizr", "A publishing platform for software architecture diagrams and documentation based upon the C4 model.");

        //Containers
        Container activeMqConsumer = structurizr.addContainer("activemq-consumer", "Provides all of the server-side functionality of Structurizr, serving static and dynamic content to users.", "Java and Spring MVC");
        Container activeMqProducer = structurizr.addContainer("activemq-producer", "Stores information about users, workspaces, etc.", "Relational Database Schema");
        Container activeMqBroker = structurizr.addContainer("activemq-broker", "Stores information about users, workspaces, etc.", "Relational Database Schema");
        Container healthCheck = structurizr.addContainer("healthcheck", "Stores information about users, workspaces, etc.", "Relational Database Schema");

        //database.addTags(DATABASE_TAG);
        //webApplication.uses(database, "Reads from and writes to", "JDBC");

        //liens
        activeMqConsumer.uses(activeMqProducer,"send message to");
        activeMqProducer.uses(activeMqProducer,"read message from");
        healthCheck.uses(activeMqProducer,"health check");
        healthCheck.uses(activeMqConsumer,"health check");
        healthCheck.uses(activeMqBroker,"health check");

        //Deploiement
        DeploymentNode jelastic = model.addDeploymentNode("Jelastic", "", "");

        //environnement activemq
        DeploymentNode activemqEnv = jelastic.addDeploymentNode("Environnement ActiveMQ", "", "");

        ContainerInstance activeMqContainer = activemqEnv.add(activeMqBroker);
        activeMqContainer.addHealthCheck("ActiveMQ Broker status", "localhost:8080/actuator/health");

        ContainerInstance healthCeckContainer = activemqEnv.add(healthCheck);
        healthCeckContainer.addHealthCheck("HealthCheck status", "localhost:8080/actuator/health");

        //environnement services
        DeploymentNode serviceEnv = jelastic.addDeploymentNode("Envirnnement Service", "", "");



        ContainerInstance consumerContainer = serviceEnv.add(activeMqConsumer);
        consumerContainer.addHealthCheck("Consumer status","");

        ContainerInstance producerContainer = serviceEnv.add(activeMqProducer);
        producerContainer.addHealthCheck("Producer status","");



        // the pass/fail status from the health checks is used to supplement any deployment views that include the container instances that have health checks defined
        DeploymentView deploymentView = views.createDeploymentView(structurizr, "Deployment", "A deployment diagram showing the live environment.");
        deploymentView.setEnvironment("Live");
        deploymentView.addAllDeploymentNodes();

        views.getConfiguration().getStyles().addElementStyle(Tags.ELEMENT).color("#ffffff");
        views.getConfiguration().getStyles().addElementStyle(JMS_TAG).shape(Shape.Cylinder);


        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }


}