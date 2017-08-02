package com.qatang.team.algorithm.tpf.spi;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author qatang
 */
public class Junzhuan {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);


        List<String> list = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (StringUtils.isEmpty(line)) {
                break;
            }
            String[] content = StringUtils.split(line, "\t");

            StringBuilder sb = new StringBuilder();
            sb.append("insert into bj_junzhuan (job_num, company, dept, position, position_count, interview, position_prop, position_type, position_dist, position_addr, position_memo, tel) values ('");
            sb.append(content[0]);
            sb.append("','");
            sb.append(content[1]);
            sb.append("','");
            sb.append(content[2]);
            sb.append("','");
            sb.append(content[3]);
            sb.append("','");
            sb.append(content[4]);
            sb.append("','");
            sb.append(content[5]);
            sb.append("','");
            sb.append(content[6]);
            sb.append("','");
            sb.append(content[7]);
            sb.append("','");
            sb.append(content[8]);
            sb.append("','");
            sb.append(content[9]);
            sb.append("','");
            sb.append(content[10]);
            sb.append("','");
            sb.append(content[11]);
            sb.append("');");
            list.add(sb.toString());

        }
        Path path = Paths.get("/Users/qatang/Dropbox/junzhuang.sql");
        try {
            Files.write(path, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
