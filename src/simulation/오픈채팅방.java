package simulation;

import java.util.*;
import java.util.logging.Handler;

public class 오픈채팅방 {
    private static String enter = "님이 들어왔습니다.";
    private static String leave = "님이 나갔습니다.";

    public String[] solution(String[] record) {
        Map<String, String> userMap = new HashMap<>();

        for (String s : record) {
            String[] command = s.split(" ");
            if (command[0].equals("Enter") || command[0].equals("Change")) userMap.put(command[1], command[2]);
        }

        List<String> commandList = new ArrayList<>();
        for (String s : record) {
            String[] command = s.split(" ");
            StringBuilder sb = new StringBuilder();
            sb.append(userMap.get(command[1]));
            switch (command[0]) {
                case "Enter" : sb.append(enter);
                    break;
                case "Leave" : sb.append(leave);
                    break;
                case "Change" : continue;
            }

            commandList.add(sb.toString());
        }

        return commandList.toArray(new String[commandList.size()]);
    }
}
