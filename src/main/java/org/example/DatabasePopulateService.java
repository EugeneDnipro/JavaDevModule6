package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabasePopulateService {

    private static List<WorkerDataDto> getWorkerData(String pathToFile) {
        List<String> parsedWorkerData;
        List<WorkerDataDto> workerLines = new ArrayList<>();
        try (InputStream fis = new FileInputStream(pathToFile);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parsedWorkerData = List.of(line.split(","));
                workerLines.add(new WorkerDataDto(parsedWorkerData.get(0),
                        parsedWorkerData.get(1),
                        parsedWorkerData.get(2),
                        Long.parseLong(parsedWorkerData.get(3))));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return workerLines;
    }

    private static List<ClientDataDto> getClientData(String pathToFile) {
        List<ClientDataDto> clientLines = new ArrayList<>();
        try (InputStream fis = new FileInputStream(pathToFile);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                clientLines.add(new ClientDataDto(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientLines;
    }

    private static List<ProjectDataDto> getProjectData(String pathToFile) {
        List<String> parsedProjectData;
        List<ProjectDataDto> projectLines = new ArrayList<>();
        try (InputStream fis = new FileInputStream(pathToFile);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parsedProjectData = List.of(line.split(","));
                projectLines.add(new ProjectDataDto(parsedProjectData.get(0),
                        parsedProjectData.get(1),
                        parsedProjectData.get(2)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return projectLines;
    }

    private static List<ProjectWorkerDataDto> getProjectWorkerData(String pathToFile) {
        List<String> parsedProjectWorkerData;
        List<ProjectWorkerDataDto> projectWorkerLines = new ArrayList<>();
        try (InputStream fis = new FileInputStream(pathToFile);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parsedProjectWorkerData = List.of(line.split(","));
                projectWorkerLines.add(new ProjectWorkerDataDto(Integer.parseInt(parsedProjectWorkerData.get(0)),
                        Integer.parseInt(parsedProjectWorkerData.get(1))));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return projectWorkerLines;
    }

    public static void main(String[] args) {
        final String WORKER_DATA_FILE = "sql/worker_data.txt";
        final String CLIENT_DATA_FILE = "sql/client_data.txt";
        final String PROJECT_DATA_FILE = "sql/project_data.txt";
        final String PROJECT_WORKER_DATA_FILE = "sql/project_worker_data.txt";

        String workerTableSqlTemplate = "INSERT INTO worker (name, birthday, level, salary) VALUES (?, ?, ?, ?);";
        String clientTableSqlTemplate = "INSERT INTO client (name) VALUES (?);";
        String projectTableSqlTemplate = "INSERT INTO project (client_id, start_date, finish_date) VALUES (?, ?, ?);";
        String project_workerTableSqlTemplate = "INSERT INTO project_worker (project_id, worker_id) VALUES (?, ?);";

        try (PreparedStatement workerTableQueryStatement = Database.getInstance().getConnection().prepareStatement(workerTableSqlTemplate)) {
            for (WorkerDataDto dto : getWorkerData(WORKER_DATA_FILE)) {
                workerTableQueryStatement.setString(1, dto.getName());
                workerTableQueryStatement.setString(2, dto.getBirthday());
                workerTableQueryStatement.setString(3, dto.getLevel());
                workerTableQueryStatement.setLong(4, dto.getSalary());
                workerTableQueryStatement.addBatch();
            }
            workerTableQueryStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement clientTableQueryStatement = Database.getInstance().getConnection().prepareStatement(clientTableSqlTemplate)) {
            for (ClientDataDto dto : getClientData(CLIENT_DATA_FILE)) {
                clientTableQueryStatement.setString(1, dto.getName());
                clientTableQueryStatement.addBatch();
            }
            clientTableQueryStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement projectTableQueryStatement = Database.getInstance().getConnection().prepareStatement(projectTableSqlTemplate)) {
            for (ProjectDataDto dto : getProjectData(PROJECT_DATA_FILE)) {
                projectTableQueryStatement.setString(1, dto.getClient_id());
                projectTableQueryStatement.setString(2, dto.getStart_date());
                projectTableQueryStatement.setString(3, dto.getFinish_date());
                projectTableQueryStatement.addBatch();
            }
            projectTableQueryStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement project_workerTableQueryStatement = Database.getInstance().getConnection().prepareStatement(project_workerTableSqlTemplate)) {
            for (ProjectWorkerDataDto dto : getProjectWorkerData(PROJECT_WORKER_DATA_FILE)) {
                project_workerTableQueryStatement.setInt(1, dto.getProject_id());
                project_workerTableQueryStatement.setInt(2, dto.getWorker_id());
                project_workerTableQueryStatement.addBatch();
            }
            project_workerTableQueryStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
