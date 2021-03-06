package de.tuberlin.hdis14.cinema;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.jaunt.Element;
import com.jaunt.NodeNotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;



public class CinemaDetails implements ICinemaDetails {

	String movieName;

	String durationInfo;

	List<Cinema> cinemas;
	List<String> movies;

	private String streetAddress="theodor heuss platz";
	private String houseNumber="5";
	private String zipCode="14052";


	/* (non-Javadoc)
	 * @see de.tuberlin.hdis14.cinema.ICinemaDetails#getCinemaDetails(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Cinema> getCinemaDetails(String near,String movie) throws ResponseException, NodeNotFound
	{
		UserAgent userAgent = new UserAgent();

		String[] splits=movie.split(" ");

		StringBuffer x1=new StringBuffer();

		for(int i=0;i<splits.length-1;i++)
		{
			x1.append(splits[i]+"%");
		}
		x1.append(splits[splits.length-1]);
		userAgent.visit("http://www.google.de/movies?near="+near+"&q="+x1.toString());


		String moviename= userAgent.doc.findFirst("<div class=movie>").getElement(0).getElement(0).getElement(0).innerText();
		this.movieName=moviename;
	//	System.out.println("movie"+movieName);

		String desc= userAgent.doc.findFirst("<div class=movie>").getElement(0).getElement(0).getElement(1).getText().replace("&#8206;", "");
		this.durationInfo=desc;
	//	System.out.println(desc);


		List<Element> elementsLeft = userAgent.doc.findFirst("<div class=showtimes>").getElement(0).getChildElements();            //find all divs in the document
		List<Element> elementsRight = userAgent.doc.findFirst("<div class=showtimes>").getElement(1).getChildElements();            //find all divs in the document

		cinemas=new ArrayList();

		for(int i=0;i<elementsLeft.size();i++)
		{
			Element x = elementsLeft.get(i);
			String name=x.getElement(0).getElement(0).innerText();
			String address=x.getElement(0).getElement(1).innerText();
			String times=x.getElement(1).innerText().replace("&#8206;", "").replace("&nbsp", "");
			String[] timesx=times.split(" ");
			int[] endtime = new int[timesx.length];
			String[] endtimes = new String[timesx.length];

			for(int j=0;j<timesx.length;j++)
			{
				int start= Integer.parseInt(timesx[j].substring(0, 2));

				int end= start+Integer.parseInt((Integer.parseInt(durationInfo.split("Std.")[0].trim())>2)?(durationInfo.split("Std.")[0].trim()):"2");

				endtimes[j]=String.valueOf(end)+timesx[j].substring(2);

			}

			for(int l=0;l<endtimes.length;l++)
			{
				Calendar cal = Calendar.getInstance(); 

				String tm[]=       endtimes[l].split(":");

				cal.set(Calendar.HOUR, Integer.parseInt(tm[0])); 
				cal.set(Calendar.MINUTE, Integer.parseInt(tm[1])); 
				cal.set(Calendar.SECOND, 0); 

				Date dt = cal.getTime();
				int unixTime=	(int)dt.getTime()/1000;
				endtime[l]=unixTime;
			}



			Cinema cin=new Cinema(name,timesx,address, endtime);
			cinemas.add(cin);
		}
		for(int i=0;i<elementsRight.size();i++)
		{
			Element x = elementsRight.get(i);
			String name=x.getElement(0).getElement(0).innerText();
			String address=x.getElement(0).getElement(1).innerText();
			String times=x.getElement(1).innerText().replace("&#8206;", "").replace("&nbsp", "");

			String[] timesx=times.split(" ");

			String[] endtimes = new String[timesx.length];
			int[] endtime = new int[timesx.length];

			for(int j=0;j<timesx.length;j++)
			{
				int start= Integer.parseInt(timesx[j].substring(0, 2));

				int end= start+Integer.parseInt((Integer.parseInt(durationInfo.split("Std.")[0].trim())>2)?(durationInfo.split("Std.")[0].trim()):"2");

				endtimes[j]=String.valueOf(end)+timesx[j].substring(2);

			}


			for(int l=0;l<endtimes.length;l++)
			{
				Calendar cal = Calendar.getInstance(); 

				String tm[]=       endtimes[l].split(":");

				cal.set(Calendar.HOUR, Integer.parseInt(tm[0])); 
				cal.set(Calendar.MINUTE, Integer.parseInt(tm[1])); 
				cal.set(Calendar.SECOND, 0); 

				Date dt = cal.getTime();
				int unixTime=	(int)dt.getTime()/1000;
				endtime[l]=unixTime;
			}



			Cinema cin=new Cinema(name,timesx,address, endtime);
			cinemas.add(cin);
		}

		return cinemas;

	}

	public List<String> getMovieList(String near) throws ResponseException, NodeNotFound
	{
		UserAgent userAgent = new UserAgent();

		movies=new ArrayList();


		for(int i=50;i<140;i++)
		{
			userAgent.visit("http://www.google.de/movies?near="+near+"&sort=1&date=1&start="+i);

			List<Element> elements= userAgent.doc.findFirst("<div class=movie_results>").getChildElements();




			for(int i1=0;i1<elements.size();i1++)
			{
				Element x = elements.get(i1);
				String name=x.getElement(0).getElement(0).getElement(0).getElement(0).innerText();
				if(name.contains("OmU")||name.contains("OV"))
				{ 	//System.out.println(name);
					movies.add(name);
				}
			}
		}


		return movies;
	}

	public ArrayList<String> removeDuplicates(List<String> movies2){
		System.out.println("i am here");
		return new ArrayList<String>(new HashSet<String>(movies2));
	}

	public static void main(String[] args) throws ResponseException, NodeNotFound
	{

		CinemaDetails check=new CinemaDetails();


		List<Cinema> cinema = check.getCinemaDetails("berlin", "Robocop (OV)");
/*	List<String> movies = check.getMovieList("berlin");
		movies=check.removeDuplicates(movies);

		for(int i=0;i<movies.size();i++)
		{

			System.out.println("name="+movies.get(i));

			System.out.println("\n------------------------");

		} */
				System.out.println(cinema.size());
		System.out.println(check.movieName);
		System.out.println("duration"+check.durationInfo.split("Std.")[0]);
		for(int i=0;i<cinema.size();i++)
		{
			Cinema x = cinema.get(i);
			System.out.println("name="+x.getTheaterName());
			System.out.println("address:"+x.getAddress());
			System.out.print("times:");
			String[] time=x.getScreeningTime();
			for(int k=0;k<time.length;k++){
				System.out.print(time[k]+",");
			}
			System.out.println();
			System.out.print("endingtimes:");
			int[] times=x.getMovieEndTime();
			for(int k=0;k<times.length;k++){
				System.out.print(times[k]+",");
			}
			System.out.println("\n------------------------");

		}
		 
	}

}

