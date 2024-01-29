package week11.n4_행복유치원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Width> widths = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            arr.add(Integer.valueOf(st.nextToken()));
        }

        Collections.sort(arr);

        ArrayList<Integer> lines = new ArrayList<>();


        for(int i = 1; i < arr.size(); i++) {
            int before =  arr.get(i - 1);
            widths.add(new Width(i - 1, arr.get(i) - before));
        }

        Collections.sort(widths);

        for(int i = 0; i < K - 1; i++) {
            lines.add( widths.get(i).index );
        }

        Collections.sort(lines);

        int result = 0;
        int now = 0;
        for(int num : lines) {
            result += arr.get(num) - arr.get(now);
            now = num + 1;
        }
        result += arr.get(arr.size() - 1) - arr.get(now);

        System.out.println(result);

    }
}

class Width implements Comparable<Width> {
    int index;
    int value;

    public Width(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int compareTo(Width o) {
        return Integer.compare(o.value, this.value);
    }
}