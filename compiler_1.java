import java.io.*;

public class compiler_1 {
	public static String str[] = new String[10];
	public static String str2[];
	public static String token[];
	public static int cnt = 0;
	public static int flg = 0;
	public static int flg2 = 0;		//エラーメッセージを1つだけにする
	public static int flg3 = 0;

	public static void main(String args[]) {
		try {
			File f = new File("sample.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));

			int c = br.read();
			while (c != -1) {
				put((char)c);	//読み込んだ文字を配列に格納
				c = br.read();
			}
			if(flg == 1) {		//最後が数値だったら
				cnt++;
			}

			br.close();
			check();			//エラーチェック
			if(flg2 == 0) {		//エラーチェックでエラーがなかったら
				token();		//トークン生成
				if(flg3 == 0) {//トークン生成にエラーがなかったら
					print();	//生成したトークンを印字
				}else {			//トークン生成時のエラー
					System.out.print("演算子以外の記号があります");
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}


	public static void put(char c)  {				//読み込んだ文字を配列に格納
		if(c != ' ') {
			if((int)c >= 48 && (int)c <= 57) {		//数字かどうか
				if(str[cnt] == null) {				//数字の時、前に値が入っているか
					str[cnt] = String.valueOf(c);	//入っていないとき
					flg = 1;
				}else {
					str[cnt] = str[cnt] + String.valueOf(c);	//入っていた時
				}
			}else { 								//数字以外(記号)のとき
				flg = 0;
				cnt++;
				str[cnt] =  String.valueOf(c);
				cnt++;
			}
		}else {		//空白のとき
			flg = 0;
		}
	}


	public static void check() {					//エラーチェック
		str2 = new String[cnt];
		for(int i = 0; i <= cnt-1; i++) {				//str[]の中身をチェック
			if(str[i] == null) {						//途中でnullが入ってないか
				System.out.print("式が間違っています");
				flg2 = 1;
				break;
			}
		}

		if(flg2 != 1) {		//一度もエラーメッセージを出してないか
			try {
				int a = Integer.parseInt(str[cnt-1]);
			}catch (java.lang.NumberFormatException  e) {
				System.out.println( "式が間違っています");
			}
		}

		System.arraycopy (str,0,str2,0,cnt); 		//str2[i] = str[i]  str2にnullを入れず格納
	}



	public static void token() {					//トークン生成
		token = new String[cnt];
		for(int i = 0; i <= cnt-1; i++) {
			try {
				int val = Integer.parseInt(str2[i]);
				token[i] = "NUMBER";
			}catch (java.lang.NumberFormatException  e) {
				switch (str2[i]) {
				case "+":
					token[i] = "PLUS";
					break;
				case "-":
					token[i] = "MINUS";
					break;
				case "*":
					token[i] = "TIMES";
					break;
				case "/":
					token[i] = "DIVIDED";
					break;
				default:
					flg3 = 1;	//演算子以外の記号のとき
					break;
				}
			}
		}
	}


	public static void print() {					//生成したトークンを印字
		System.out.print("[ ");
		for(int i = 0; i <= cnt-1; i++) {
			System.out.print(token[i] + " ");
		}
		System.out.print("]");
		System.out.println();
	}





}



