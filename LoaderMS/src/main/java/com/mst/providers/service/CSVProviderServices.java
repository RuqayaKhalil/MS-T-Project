package com.mst.providers.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.mst.beans.EntryInfo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class CSVProviderServices {

    public List<EntryInfo> loadCsvFromFolder(String filePath) throws IOException {
        List<EntryInfo> entries = new ArrayList<>();  // Store entries
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();  // Read header row first
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                EntryInfo entry = new EntryInfo();
                for (int i = 0; i < nextLine.length; i++) {
                    String columnName = headers[i];                  
                    switch (columnName) {
                        case "manager_id":
//                        case "manager_id":
                        case "owner_id":
                        	entry.setOwner_id(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "projects":
//                        case "projects":
                        case "project":
                        	entry.setProject(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "assignee":
//                        case "assignee":
                        case "tag":
                        	entry.setTag(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "label":
//                        case "label":
//                        case "label":
                        	entry.setLabel(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "employeeID":
                        case "developer_id":
                        case "worker_id":
                        	entry.setDeveloper_id(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "issue":
//                        case "issue":
                        case "task":
                        	entry.setTask_number(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "env":
                        case "environment":
                        case "pr_env":
                        	entry.setEnvironment(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "user_story":
//                        case "user_story":
//                        case "user_story":
                        	entry.setUser_story(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "point":
//                        case "point":
                        case "day":
                        	entry.setTask_point(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        case "sprint":
//                        case "sprint":
                        case "currant_sprint":
                        	entry.setSprint(nextLine[i]!= null ? nextLine[i] : "");
                            break;

                        default:
                            // Handle unexpected columns
                            break;
                    }
                }

                entries.add(entry);  // Add the EntryInfo object to the list
            }

        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        return entries;  // Return the list of entries
    }
}

