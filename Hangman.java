import java.util.*;
import java.util.Map.Entry;
import java.util.Map.*;
import java.io.*;
public class Hangman {
	
	private static int incorrectguesses=0;
	private static String nice="";
	private static String initialnice="";
	private static boolean onwards=true;
	
	
	public static Map<String,List<String>> createWordFamilies(List<String>wordList, Set<Character> guessed){
		String bestone="";
		Map<String,List<String>> bestoption=new HashMap<>();
		Map<String,List<String>> families =  new HashMap<>();
		for(String word:wordList) {
			String family  =  getWordFamily(word, guessed);
			if((families.containsKey(family))){
				List<String>output=families.get(family);
				output.add(word);
				families.put(family, output);
			}else {
				List<String>output1= new ArrayList<>();
				output1.add(word);
				families.put(family, output1);
		}
		 bestone=getBestFamily(families);
		 nice=bestone;
	}
		bestoption.put(bestone, families.get(bestone));
		
		return bestoption;	
	}
		

	
	public static String getWordFamily(String word , Set<Character> guessed) {  //plays a single round of hangman on a single word
		String family = "";
		for(char c : word.toCharArray() ) {
			if(guessed.contains(c)) {
				family=family+c;
			}else {
				family=family+"-";
			}
		}
		return family;
	}
	
	
	public static String getBestFamily(Map<String,List<String>> wordFamilies) {
		int count=0;
		String bestoption="";
		for(String subset:wordFamilies.keySet()) {
			if(wordFamilies.get(subset).size()>count) {
				count=wordFamilies.get(subset).size();
				bestoption=subset;
			}
		}
		return bestoption;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Map<Integer,List<String>> wordsList= new HashMap<>();
		String filename="words.txt";
		Scanner in= new Scanner(new File(filename));
		while(in.hasNextLine()){
			List<String>output=new ArrayList<>();
			String word=in.nextLine();
			word=word.toLowerCase();
			int length=word.length();
			if(!(wordsList.containsKey(length))){
				output.add(word);
		}else {
			output=wordsList.get(length);
			output.add(word);
		}
			wordsList.put(length, output);
	}
		
		
		
		
		
		String test="";
		int guesses=0;
		Scanner question=new Scanner(System.in);
		System.out.println(" What is the size of the hidden word you want to try to guess?");
		int amount=question.nextInt();
		if(!(wordsList.containsKey(amount))){
			System.out.println("invalid input, game ends here");
		}else {
			
			
			
			Scanner rounds=new Scanner(System.in);
			System.out.println("How many incorrect guesses are you allowed?");
			 guesses=rounds.nextInt();
				List<String> initialDictionary= wordsList.get(amount);
				Set<Character>guessedLetters= new HashSet<>();
				
				
				
				    while(incorrectguesses<guesses-1 && onwards) {
				    	List<String>convertDictionary= new ArrayList<>();
					Scanner guess= new Scanner(System.in);
					System.out.println("What is your next guess?");
					char c=guess.next().charAt(0);
					if(guessedLetters.contains(c)) {
						System.out.println("please try again, not a valid input");
					}else{
					guessedLetters.add(guess.next().charAt(0));//  https://codegym.cc/groups/posts/nextchar-in-java
					Map<String,List<String>>queen=createWordFamilies(initialDictionary,guessedLetters);
					for(String noice:queen.keySet()) {
						convertDictionary.addAll(queen.get(noice));
					}
					initialDictionary=convertDictionary;
					System.out.println(queen.keySet());
					
					//if the computer guessed the word even before the while loop broke
					for(int i=0;i<nice.length();i++) {
						char t=nice.charAt(i);
						if(t=='-') {
							break;
						}else {
							if(i==nice.length()-1) {
								System.out.println("Congrats, you guessed the word");
								onwards=false;
							}
						}
					}
					if(initialnice!="") {
						if(nice.equals(initialnice)) {
							incorrectguesses++;
						}
					}
					initialnice=nice;
					nice="";
					}
				}
				    
				    //if the while loop breaks but there are still blanks in here 
				    for(int i=0;i<initialnice.length();i++) {
				    	char c=initialnice.charAt(i);
				    	if(c=='-') {
				    		System.out.println("you lost! The correct word was  " + initialDictionary.get(0));
				    		break;
				    	}else {
				    		continue;
				    	}
				    }
		}
	}

}
