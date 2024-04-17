import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main{
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int G = Integer.parseInt(br.readLine());

    long weight1 = 1, weight2 = 2;

    StringBuilder answer = new StringBuilder();

    while(weight1 <= weight2 && weight2 < 100001){
      long pow1 = weight1 * weight1, pow2 = weight2 * weight2;

      if(pow2 - pow1 < G){
        weight2++;
      }else if(pow2 - pow1 > G){
        weight1++;
      }else{
        answer.append(weight2).append("\n");
        weight2++;
      }
    }

    System.out.print(answer.length() > 0 ? answer : -1);
  }
}