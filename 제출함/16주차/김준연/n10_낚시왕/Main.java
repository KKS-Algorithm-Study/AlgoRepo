/**
 * 접근 :
 *      1. 낚시왕의 한 칸 이동 => O(1)
 *      2. 제일 가까운 상어 잡기 => O(100)
 *      3. 상어가 이동하기
 * 	        1) 한마리 한마리 한칸 한칸 => 100 * 100 * 1000
 * 	        2) 한마리 한마리 거리 기준 나머지 연산 => 100 * 100
 * 	    4. 모든 상어가 이동하면 좌표를 문자열로 기록해 HashMap에 상어를 담음
 * 	    5. 중복되는 키가 있을 경우 더 사이즈가 큰 상어로 대체함
 * 	    6. 남은 value들의 상어로 다음 연산을 굴림
 */

package week16.n10_낚시왕;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    static int R,C,M;

    static class Shark {
        int x, y;
        int size;
        int speed;
        int direction;

        public Shark(int x, int y, int size, int speed, int direction) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.direction = direction;
        }

        void move() {

            if(speed == 0) return;

            int diff;
            switch (direction) {
                case 0 :
                    diff = x - 1;
                    if(speed < diff) {
                        this.x = x - speed;
                    }
                    else if(speed < diff+R-1) {
                        this.x = speed - diff + 1;
                        direction = 1;
                    }
                    else {
                        this.x = R - (speed - diff - (R - 1));
                    }
                    break;

                case 1 :
                    diff = R - x;
                    if(speed < diff) {
                        this.x += speed;
                    }
                    else if(speed < diff+R-1) {
                        this.x = R - (speed - diff);
                        direction = 0;
                    }
                    else {
                        this.x = speed - (diff+R-1) + 1;
                    }
                    break;
                case 3 :
                    diff = y - 1;
                    if(speed < diff) {
                        this.y = y - speed;
                    }
                    else if(speed < diff+C-1) {
                        this.y = speed - diff + 1;
                        direction = 2;
                    }
                    else {
                        this.y = C - (speed - diff - (C - 1));
                    }
                    break;

                case 2 :
                    diff = C - y;
                    if(speed < diff) {
                        this.y += speed;
                    }
                    else if(speed < diff+C-1) {
                        this.y = C - (speed - diff);
                        direction = 3;
                    }
                    else {
                        this.y = speed - (diff+C-1) + 1;
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        HashSet<Shark> sharks = new HashSet<>();

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken()) - 1;
            int size = Integer.parseInt(st.nextToken());

            if(direction < 2) {
                speed = speed % ((R-1) * 2);
            }
            else speed = speed % ((C-1) * 2);

            Shark shark = new Shark(x, y, size, speed, direction);
            sharks.add(shark);
        }

        int result = 0;

        for(int i = 1; i <= C; i++) {
            //System.out.println("상어 마리수  : " + sharks.size());
            Shark closest = new Shark(R + 2, 0, 0, 0, 0);
            for(Shark now : sharks) {
                //System.out.println(now.x + " / " + now.y + " size : " + now.size + " speed : " + now.speed);
                if(now.y == i && closest.x > now.x) {
                    closest = now;
                }
            }
            result += closest.size;
            //System.out.println("잡은 사이즈 : " + result);
            sharks.remove(closest);

            HashMap<String, Shark> duplication = new HashMap<>();

            for(Shark shark : sharks) {
                shark.move();
            }
            for(Shark shark : sharks) {
                String str = shark.x + "/"+shark.y;
                if(duplication.containsKey(str)) {
                    if(duplication.get(str).size < shark.size) {
                        //System.out.println("잡아먹음");
                        duplication.put(str, shark);
                    }
                }
                else duplication.put(str, shark);
            }
            sharks.clear();
            sharks.addAll(duplication.values());
        }

        System.out.println(result);
    }
}

