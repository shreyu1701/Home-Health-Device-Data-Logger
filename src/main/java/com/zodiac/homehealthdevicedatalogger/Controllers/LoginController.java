package com.zodiac.homehealthdevicedatalogger.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zodiac.homehealthdevicedatalogger.Data.DBConnect;
//import com.zodiac.homehealthdevicedatalogger.Models.Patient;
import com.zodiac.homehealthdevicedatalogger.Data.DataStorage;
import com.zodiac.homehealthdevicedatalogger.Models.UserSession;
import com.zodiac.homehealthdevicedatalogger.Util.EmailService;
import com.zodiac.homehealthdevicedatalogger.Util.IDGenerator;
import com.zodiac.homehealthdevicedatalogger.Util.Util;
import com.zodiac.homehealthdevicedatalogger.Validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.zodiac.homehealthdevicedatalogger.Data.UserDataManager;
import com.zodiac.homehealthdevicedatalogger.Models.User;

import javax.mail.MessagingException;

public class LoginController {


	@FXML
	private Label errorLabel;
	@FXML
	private Button btnSignUp;
	@FXML
	private Button btnLoginSubmit;
	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Hyperlink linkForgetPassword;

	private final UserDataManager userDataManager = new UserDataManager();
	Validator validator = new Validator();
	EmailService emailService = new EmailService();
	Util util = new Util();
	IDGenerator idGenerator = new IDGenerator();

	PatientDashboardController patientDashboardController = new PatientDashboardController();
	TechnicianDashboardController technicianDashboardController = new TechnicianDashboardController();
	DataStorage dataStorage = new DataStorage();

	// Login button Starts

	@FXML
	public void handleLogin(ActionEvent mouseEvent) {
		String email = txtEmail.getText();
		String password = txtPassword.getText();

		if (email.isEmpty() || password.isEmpty()) {
			showAlert("Error", "Please enter both email and password.");
			return;
		}

		String serverResponse = loginToServer(email, password);

		if (serverResponse.startsWith("SUCCESS")) {
			String[] parts = serverResponse.split(":");

//			if (parts.length == 3) {
//				String role = parts[1];
//			User user	 = new User(email, password);
			User user = dataStorage.loadUserDetail(email);
				String role = user.getRole();
				UserSession.getInstance().setCurrentUser(user);

				if ("Technician".equals(role)) {
					loadDashboard("/com/zodiac/homehealthdevicedatalogger/Views/TechnicianDashboard.fxml", user);

				} else if ("Patient".equals(role)) {
					loadDashboard("/com/zodiac/homehealthdevicedatalogger/Views/PatientDashboard.fxml", user);
				} else {
					showAlert("Error", "User role not recognized.");
				}
			}
//		} else {
//			showAlert("Login Failed", "Invalid email or password.");
//		}
	}

	private String loginToServer(String email, String password) {
		try (Socket socket = new Socket("localhost", 8100);
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			// Send login request
			out.println("LOGIN:" + email + ":" + password);

			// Read server response
			return in.readLine()+":"+email+":"+password;
		} catch (IOException e) {
			e.printStackTrace();
			showAlert("Error:", " Unable to connect to the server");
			return "Error: Unable to connect to the server. Please try again later.";
		}
	}

	private void loadDashboard(String fxmlPath, User user) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
			Parent root = loader.load();

			// Pass the user to the dashboard controller
			if (fxmlPath.contains("TechnicianDashboard")) {
				TechnicianDashboardController technicianDashboardController = loader.getController();
				technicianDashboardController.initialize(user);
				technicianDashboardController.loadTechnicianData(user);
				technicianDashboardController.loadAllHealthData();
				technicianDashboardController.loadDataTechnicianDetail(user);
			} else if (fxmlPath.contains("PatientDashboard")) {
				PatientDashboardController patientDashboardController = loader.getController();
//				patientDashboardController.loadUserData(user);
//
                    patientDashboardController.initialize(user);
                    // Pass patient data to PatientDashboardController
                    patientDashboardController.loadUserData(user);
					patientDashboardController.loadDataPatientDetail(user);

			}

			// Set the scene
			Stage stage = (Stage) btnLoginSubmit.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			showAlert("Error", "Unable to load the dashboard.");
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


	private boolean isHealthDataAvailable(String userId) {
		String query = "SELECT COUNT(*) FROM HealthData WHERE USER_ID = ?";
		try (Connection connection = DBConnect.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt(1) > 0; // Returns true if health data exists
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showAlert("Database Error", "Failed to fetch health data.");
		}
		return false;
	}


//    @FXML
//    public void handleLogin(ActionEvent mouseEvent) throws IOException {
//
//
//            String email = txtEmail.getText();
//            String password = txtPassword.getText();
//
//            if (email.isEmpty() || password.isEmpty()) {
//                showAlert("Error", "Please enter both email and password.");
//                return;
//            }
//
//            // Validate login
//            User user = inputValidator.validateUser(email, password);
//
//            User currentUser = new User(user.getId(), user.getFirstName());
//            UserSession.getInstance().setCurrentUser(currentUser);
//        if (user != null) {
//            // If user is a Technician
//            if ("Technician".equals(user.getRole())) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/TechnicianDashboard.fxml"));
//                Parent root = loader.load();
//                TechnicianDashboardController technicianDashboardController = loader.getController();
//
//                technicianDashboardController.loadTechnicianData(user);
//
//                // Set the scene and display
//                Stage stage = (Stage) btnLoginSubmit.getScene().getWindow();
//                stage.setScene(new Scene(root));
//                stage.show();
//
//
//            } else if ("Patient".equals(user.getRole())) {
//
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/PatientDashboard.fxml"));
//                    Parent root = loader.load();
//
//
//                // Get the PatientDashboardController instance
//                    PatientDashboardController patientDashboardController = loader.getController();
//
//                    patientDashboardController.initialize(user);
//                    // Pass patient data to PatientDashboardController
//                    patientDashboardController.loadUserData(user);
//
//                    // Set the scene and display
//                    Stage stage = (Stage) btnLoginSubmit.getScene().getWindow();
//                    stage.setScene(new Scene(root));
//                    stage.show();
//                } else {
//                    showAlert("Error", "Patient data not found.");
//                }
//
//        } else {
//            showAlert("Login Failed", "Invalid email or password.");
//        }
//
//
//        }

	private void passPatientDataToDashboard(User patientData) {
	}


	// Method to fetch patient-specific data from UserData.json based on user ID
	private User getUserData(String userId) throws IOException {
		// Initialize ObjectMapper to parse the JSON
		ObjectMapper objectMapper = new ObjectMapper();
		File userFile = new File("UserData.json");

		// Parse the JSON file into UserData object
		List<User> users = objectMapper.readValue(userFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

		// Iterate over the list of users and find the matching userId
		for (User user : users) {
			if (null != user.getRoleID() && user.getRoleID().equals(userId)) {
				return user; // Return the matching user data
			}
		}
		return null;
	}


//    public void loadAllHealthData() {
//        String query = "SELECT user_id, data_date, blood_pressure, sugar_level, heart_rate, oxygen_level, comments FROM HealthData";
//
//        try (Connection connection = DBConnect.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            ObservableList<PatientHealthData> healthDataList = FXCollections.observableArrayList();
//
//            // Map ResultSet to HealthData objects
//            while (resultSet.next()) {
//                PatientHealthData healthData = new PatientHealthData(
//                        resultSet.getString("user_id"),
//                        resultSet.getDate("data_date").toLocalDate(),
//                        resultSet.getString("blood_pressure"),
//                        resultSet.getString("sugar_level"),
//                        resultSet.getInt("heart_rate"),
//                        resultSet.getInt("oxygen_level"),
//                        resultSet.getString("comments")
//                );
//                healthDataList.add(healthData);
//            }
//
//            // Bind the data to the TableView
//            healthRecordsTable.setItems(healthDataList);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert("Database Error", "Failed to fetch health data for all users.");
//        }
//    }


	// Show alert messages
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	//Forget Password Starts
	@FXML
	public void handleForgetPassword(ActionEvent event) throws IOException {

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Forgot Password");
		dialog.setHeaderText("Enter your email address");
		dialog.setContentText("Email:");
		Optional<String> result = dialog.showAndWait();

		result.ifPresent(email -> {
			if (validator.isValidEmail(email)) {

				sendPasswordResetLink(email);

				showConfirmationDialog("Password has been sent to your email.");
			} else {
				showErrorDialog("Invalid email address. Please try again.");
			}
		});

	}


	//Show Error Dialog Message Box
	private void showErrorDialog(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}

	//Show Confirmation Dialog Message Box
	private void showConfirmationDialog(String s) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Email Sent");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}
	//Valid Email (Login Email && Forget Password Email)

	//Send Reset Link
	private void sendPasswordResetLink(String email) {
		try {
			emailService.sendPasswordResetLink(email);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		System.out.println("Password reset link sent to: " + email);
	}
	//Forget Password Ends


	//Sign Up Button
	@FXML
	public void handleSignUp(ActionEvent event) throws IOException {
		URL fxmlLocation = getClass().getResource("/com/zodiac/homehealthdevicedatalogger/Views/SignUp.fxml");
		GUILoader(fxmlLocation, btnSignUp);

	}

	protected static void GUILoader(URL fxmlLocation, Button btnAction) throws IOException {
		FXMLLoader loader = new FXMLLoader(fxmlLocation);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		Stage closestage = (Stage) btnAction.getScene().getWindow();
		closestage.close();
	}
}
