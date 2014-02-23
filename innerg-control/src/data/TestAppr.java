package data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * J'ai cree cette classe pou tester l'apprentissage. sachant que je ne peut
 * pas connecter mon telephone a mon ordi j'enregistre des données via mon iphone
 * je me les envoie et je fait comme si je les recevaient en direct (method simuconnect)
 * @author Nicolas
 *
 */
public class TestAppr {
	
	private Move longmove;
	
	
	
	public TestAppr() {
		super();
		this.longmove = new Move();
		read("innerg/moves/apprmove.txt");
	}



	public void read(String filepath)
	{
		try{
			
			InputStream ips=new FileInputStream(filepath); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				String[] datas = ligne.split(";");
				float[] datasFloat = new float[6];
				int size = datas.length;
				size=Math.min(size,6);
				
				for(int i=0;i<size;i++)
				{
					datasFloat[i] = Float.parseFloat(datas[i]);
				}
				
				Data data = new Data(datasFloat[0],datasFloat[1],datasFloat[2],datasFloat[3],datasFloat[4],datasFloat[5]);
								
				longmove.getMove().add(data);
			}
		br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}		
	}
	
	public void simuConnect()
	{
		int size = longmove.getMove().size();
		int count = 0;
		Float[] acc = new Float[3];
		Float[] ori = new Float[3];
		
		
		Processing proc = new Processing();
		
		proc.setMode(0);
		
		while(count<size)
		{
			acc[0] = longmove.getMove().get(count).getaX();
			acc[1] = longmove.getMove().get(count).getaY();
			acc[2] = longmove.getMove().get(count).getaZ();
			ori[0] = longmove.getMove().get(count).getoX();
			ori[1] = longmove.getMove().get(count).getoY();
			ori[2] = longmove.getMove().get(count).getoZ();
			
			proc.update(acc, ori);
			
			count++;
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
