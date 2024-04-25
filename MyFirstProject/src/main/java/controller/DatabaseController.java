package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.PasswordEncryptionWIthAes;
import model.StudentModel;
import util.StringUtils;

public class DatabaseController {
	
	



    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/college_app";
        String user = "root";
        String pass = "Belbari890";
        return DriverManager.getConnection(url, user, pass);
    }

    public int addStudent(StudentModel studentModel) {
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(StringUtils.INSERT_STUDENT);

            PreparedStatement checkUsernameSt = con.prepareStatement(StringUtils.GET_USERNAME);
            checkUsernameSt.setString(1, studentModel.getUsername());
            ResultSet checkUsernameRs = checkUsernameSt.executeQuery();

            checkUsernameRs.next();

            if (checkUsernameRs.getInt(1) > 0) {
                return -2; // Username already exists
            }

            PreparedStatement checkPhoneSt = con.prepareStatement(StringUtils.GET_PHONE);
            checkPhoneSt.setString(1, studentModel.getPhoneNumber());
            ResultSet checkPhoneRs = checkPhoneSt.executeQuery();

            checkPhoneRs.next();

            if (checkPhoneRs.getInt(1) > 0) {
                return -4; // Phone Number already exists
            }

            PreparedStatement checkEmailSt = con.prepareStatement(StringUtils.GET_EMAIL);
            checkEmailSt.setString(1, studentModel.getEmail());
            ResultSet checkEmailRs = checkEmailSt.executeQuery();

            checkEmailRs.next();

            if (checkEmailRs.getInt(1) > 0) {
                return -3; // Email already exists
            }

            // Encrypt password before storing it in the database
            String encryptedPassword = PasswordEncryptionWIthAes.encryptPassword(studentModel.getPassword(), "U3CdwubLD5yQbUOG92ZnHw==");

            st.setString(1, studentModel.getUsername());
            st.setString(2, studentModel.getFirstName());
            st.setString(3, studentModel.getLastName());
            st.setDate(4, Date.valueOf(studentModel.getDob()));
            st.setString(5, studentModel.getGender());
            st.setString(6, studentModel.getEmail());
            st.setString(7, studentModel.getPhoneNumber());
            st.setString(8, studentModel.getSubject());
            st.setString(9, encryptedPassword);
            st.setString(10, studentModel.getImageUrlFromPart());

            int result = st.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getStudentLoginInfo(String username, String password) {
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(StringUtils.GET_LOGIN_STUDENT_INFO);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String userDb = rs.getString("user_name");
                String encryptedPassword = rs.getString("password");

                // Decrypt password from database and compare
                String decryptedPassword = PasswordEncryptionWIthAes.decryptPassword(encryptedPassword, "U3CdwubLD5yQbUOG92ZnHw==");

                if (decryptedPassword!=null && userDb.equals(username) && decryptedPassword.equals(password)) {
                    return 1; // Login successful
                } else {
                    return 0; // Password mismatch
                }
            } else {
                // No matching record found
                return 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public StudentModel getStudentDetails(String userName) throws ClassNotFoundException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(StringUtils.GET_LOGIN_STUDENT_INFO)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Fetch student details from the result set and create a Student object
                    StudentModel student = new StudentModel();
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));
                    // Populate other fields as needed
                    return student;
                } else {
                    // No student found with the given username
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
            return null;
        }
    }
    
    public List<StudentModel> getAllStudents() {
        List<StudentModel> students = new ArrayList<>();
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(StringUtils.GET_ALL_STUDENTS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                StudentModel student = new StudentModel();
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                // Populate other fields as needed
                students.add(student);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
        }
        return students;
    }
    
    public int deleteStudentInfo(int id) {
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(StringUtils.DELETE_STUDENT_INFO_BY_ID);
            st.setInt(1, id);

            int result = st.executeUpdate();
            return result > 0 ? 1 : 0; // Return 1 if deletion is successful, otherwise return 0
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return -1; // Return -1 for any exceptions
        }
    }
    
    public int updateStudentInfo(StudentModel studentModel) {
        try (Connection con = getConnection()) {
            PreparedStatement st = con.prepareStatement(StringUtils.UPDATE_STUDENT);

            // Encrypt password before storing it in the database
            String encryptedPassword = PasswordEncryptionWIthAes.encryptPassword(studentModel.getPassword(), "U3CdwubLD5yQbUOG92ZnHw==");

            st.setString(1, studentModel.getFirstName());
            st.setString(2, studentModel.getLastName());
            st.setDate(3, Date.valueOf(studentModel.getDob()));
            st.setString(4, studentModel.getGender());
            st.setString(5, studentModel.getEmail());
            st.setString(6, studentModel.getPhoneNumber());
            st.setString(7, studentModel.getSubject());
            st.setString(8, encryptedPassword);
            st.setString(9, studentModel.getImageUrlFromPart());
            st.setString(10, studentModel.getUsername()); // Use username to identify the student to update

            int result = st.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log the exception for debugging
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    
   
}
