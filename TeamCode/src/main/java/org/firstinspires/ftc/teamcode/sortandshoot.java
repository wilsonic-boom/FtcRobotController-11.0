package org.firstinspires.ftc.teamcode;//this sorts and shoots:
import java.util.ArrayList;
import java.util.Arrays;

public class sortandshoot {

    public static void main(String[] args) {
        // Initialize motif
        ArrayList<ArrayList<String>> motif = new ArrayList<>();
        motif.add(new ArrayList<>(Arrays.asList("R","R","G")));
        motif.add(new ArrayList<>(Arrays.asList("R","R","G")));

        // Initialize order
        ArrayList<String> order = new ArrayList<>(Arrays.asList("G","R","R"));

        shoot(motif, order);

    }

    // Shoot function
    public static void shoot(ArrayList<ArrayList<String>> motif, ArrayList<String> order) {
        while (order.size() > 0) {
            // Rotate until the first element of order matches motif[1][0]
            while (!order.get(0).equals(motif.get(1).get(0))) {
                if (order.size() > 0) {
                    order.add(0, order.get(order.size() - 1));
                    order.remove(order.size() - 1);
                }
            }
            order.remove(0);         // Remove first element of order
            motif.get(1).remove(0);  // Remove first element of motif[1]

        }
        // Remove motif[1] and append motif[0] at the end
        ArrayList<String> firstMotif = motif.get(0);
        motif.remove(1);
        motif.add(firstMotif);
    }
}