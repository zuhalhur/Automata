import java.util.*;
import java.io.*;
public class PushDowns {
	
	Stack<String> s = new Stack<String>();
	
	public String [] Lines = new String[6];
	public String [] Q;//s,p,q
	public String[] E;//input alphabet
	public String [] T;//A,Z 
	public State firstState;//s
	public String Z;
	 public String[] F;//q
	 public String input;//aaabbb or ababbcbbaba
	 
	
	 public String [] Line1;
    public State [] states;
     
    public String[] transitionSets;
    public State[] lastState;
    
    
	public void initialState(State firstState) {
		this.firstState = firstState;
	}

	public void readFile() throws IOException{
		BufferedReader read;
		File file = new File("3_1_1.txt");
		read = new BufferedReader(new FileReader(file));
		String line;
		int count = 0;
		 while ((line = read.readLine()) != null){
				Lines[count] = line;
				count++;
			}
		 transitionSets = new String[count-1];
	}
	
	public void allOperations(){
		//firstLine
		Line1 = Lines[0].split(" ");
		
		//States
		Line1[0] = Line1[0].substring(Line1[0].indexOf("{") + 1);
		Line1[0] = Line1[0].substring(0,Line1[0].indexOf("}"));
		Q = Line1[0].split(",");
		
		//import states
		int j = Q.length;
		states = new State[j];
		for(int i=0;i<Q.length;i++){
			states[i] = new State(Q[i]);
		}
		
		//insert E 
		Line1[1] = Line1[1].substring(Line1[1].indexOf("{") + 1);
		Line1[1] = Line1[1].substring(0,Line1[1].indexOf("}"));
		E = Line1[1].split(",");
		
		//insert T
		Line1[2] = Line1[2].substring(Line1[2].indexOf("{") + 1);
		Line1[2] = Line1[2].substring(0,Line1[2].indexOf("}"));
		T = Line1[2].split(",");
		
		//initialize F
		firstState = new State(Line1[4].substring(2, 3));
		Line1[5] = Line1[5].substring(Line1[5].indexOf("{") + 1);
		Line1[5] = Line1[5].substring(0,Line1[5].indexOf("}"));
		F = Line1[5].split(",");
		int size = F.length;
		lastState = new State[size];
		for(int i=0;i<lastState.length;i++){
			lastState[i] = new State(F[i]);
		}
		
		//initialize Z
		Z = Line1[3].substring(2, 3);
		s.push(Line1[3].substring(2, 3));
		
		
		//transition
		for(int i=0;i<transitionSets.length;i++){
			if(i==0){
				transitionSets[i] = Lines[i+1].substring(Lines[i+1].indexOf("{")+1);
			}
			else if(i==transitionSets.length-1){
				transitionSets[i] = Lines[i+1].substring(0, 9);
			}
			else {
			transitionSets[i] = Lines[i+1];
			}
		}
		
	}
	//Is input valid?
	public boolean isHere(String a){
		boolean yes = false;
		for(int i=0;i<E.length;i++){
			if(a.equals(E[i]) || a.equals(" ")){
				yes = true;
			}
		}
		return yes;
	}
	
	//control to the input ,according to the transition.
	public void transitionToControl(String input){
		int i=0;
		int counter=0;
		while(i<input.length()){
			String character = input.substring(i, i+1);
			if(isHere(character)){
				if(character==" ")
					character = "e";
				for(int j=0;j<transitionSets.length;j++){
					if(firstState.name.equals(transitionSets[j].substring(0, 1)) && character.equals(transitionSets[j].substring(2, 3))){
						if(transitionSets[j].substring(4, 5).equals("e") ){
							firstState.name = transitionSets[j].substring(6, 7);
							if(transitionSets[j].substring(8, 9).equals("e")){
							}
				
						else{
								s.push(transitionSets[j].substring(8, 9));
							}
							counter++;
							break;
						}
						else if(s.peek().equals(transitionSets[j].substring(4, 5))){
							firstState.name = transitionSets[j].substring(6, 7);
                            if(transitionSets[j].substring(8, 9).equals("e")){
								s.pop();
							}
							else{
							s.pop();
							s.push(transitionSets[j].substring(8, 9));
							}
							counter++;
							break;
						}
				}
			}
				
		}
			else{
				System.out.println("This input is invalid..");
			}
			i++;
	  }
		for(int k=0;k<transitionSets.length;k++){
			if(firstState.name.equals(transitionSets[k].substring(0, 1)) && "e".equals(transitionSets[k].substring(2, 3))&& s.peek().equals(transitionSets[k].substring(4, 5))&& counter==input.length()){
				firstState.name = transitionSets[k].substring(6, 7);
				if(transitionSets[k].substring(8, 9).equals("e")){
					while(!s.empty()){
						s.pop();
					}
				}
			}
		}
		
		if(firstState.name.equals(F[0]) && (s.empty() || s.peek().equals(Z)) && counter == input.length()){
			
			System.out.println("*******This input is accepted*******");
		
		}
		
		else 
			
			System.out.println("*******This input is rejected*******");
	}
	
	//take the input from file
	public String input(){
    	String [] temp =Lines[5].split(" ");
    	input = temp[1];
    	System.out.println("Input is : " + input);
    	return input;
    }
	
	
	public void callToAllMethods() throws IOException{
		PushDowns p=new PushDowns();
	
		p.readFile();
		p.allOperations();
		p.transitionToControl(p.input());
		
	}
	
	
}
