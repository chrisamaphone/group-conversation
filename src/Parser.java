/**
*Parses data from seminar.cep output
**/
import java.util.*;
import java.io.*;
public class Parser{
	public static void main(String[]args) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File("File.txt"));
		ArrayList<Actor> actors = new ArrayList<Actor>();
		ArrayList<String> beliefChanges = new ArrayList<String>();

		/* testing file scanner */
		while(scanner.hasNextLine()){
		if (scanner.hasNextLine()){
			String line = scanner.nextLine();
			if (line.contains(": character")){ //lines that declare characters are the only ones that have this.
				line = line.replace(":",""); //so names don't have a colon in them.
				String [] lineArray = line.split(" ");
				actors.add(new Actor(lineArray[0])); //first index is name
			    } 
			if (line.contains("[")){ //only last lines contain this; last lines name rules.
				String info = line.substring(line.indexOf("=")); //want everything after '='
				String [] infoArray = info.split(" "); //name is always 3rd; 0 is =, 1 is rule, 2 is topic
				for (int i = 0; i < actors.size(); i++){
					if(info.contains("speak") || info.contains("interrupt") || 
						info.contains("agree") || info.contains("change") || 
						info.contains("question")){ //phrases found in speaking rules
						if (infoArray[3].equals(actors.get(i).name)){
							actors.get(i).incrementSpeakingTime();
						    }
					    }
					if (info.contains("positive_") || info.contains("negative_") || info.contains("neutral_")){ //only belief changes have this
							if(infoArray[3].equals(actors.get(i).name)){
								//info = info.replace("=", ""); //so no = in output
								beliefChanges.add(infoArray[3] + " goes from having a " + infoArray[1].replace("_", " ") + " about the " + infoArray[2] + ".");
							}
					   }
				    }
			    }
			if (line.contains("is ")){ //lines that declare archetypes are the only ones that have this.
				String [] lineArray = line.split(",");
				for (int i = 0; i < lineArray.length; i++){
					for (int j = 0; j < actors.size(); j++){
						if (lineArray[i].contains("is ") && lineArray[i].contains(actors.get(j).name)){
							String info = lineArray[i];
							info = info.replace(")", " "); //so types don't have ).
							String [] infoArray = info.split(" "); //(is X Y--Y is always at index 3
							actors.get(j).type = infoArray[3];
						    }
					    }
				    }
			    }
		    }
		}

		for (int k = 0; k < actors.size(); k++){
			System.out.println(actors.get(k).name + " speaks " +actors.get(k).speakingTime + " time(s) and is of type " + actors.get(k).type.replace("_", " ") + ".");
		}

		for (int k = 0; k < beliefChanges.size(); k++){
			System.out.println(beliefChanges.get(k));
		}
		
    }

	/**
*represents character in model
*/
	public static class Actor{
		String name;
		String type;
		int speakingTime;
		/**Constructor**/
		public Actor(String name){
			this.name = name;
			speakingTime = 0;
		}
		public void incrementSpeakingTime(){
			speakingTime++;
		}
	}
}
