package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderImporter {
    public List<Order> importCSV(String file) throws IOException {
        List<Order> orders = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        reader.readLine(); // skip header

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            Order o = new Order(
                    data[0], data[1], data[2], data[3], data[4],
                    data[5], data[6], data[7], Integer.parseInt(data[8]),
                    Double.parseDouble(data[9]), Double.parseDouble(data[10]),
                    Double.parseDouble(data[11]), Double.parseDouble(data[12]),
                    Double.parseDouble(data[13])
            );

            orders.add(o);
        }

        reader.close();
        return orders;
    }
}
