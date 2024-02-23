/**
 * 접근 :
 *      구현
 */

package week15.n1_새끼치기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        HashSet<Bug> hs = new HashSet<>();
        hs.add(new Bug(1, 0));
        for(int i = 2; i <= N; i++) {

            int size = hs.size();

            HashSet<Bug> removed = new HashSet<>();

            for (Bug bug : hs) {
                bug.count++;
                if (bug.count == 4 - bug.birth) removed.add(bug);
            }

            hs.removeAll(removed);

            for(int j = 0; j < size; j++) {
                hs.add(new Bug(i % 2, 0));
            }
        }

        System.out.println(hs.size());

    }
}

class Bug {
    int birth;
    int count;

    public Bug(int birth, int count) {
        this.birth = birth;
        this.count = count;
    }
}