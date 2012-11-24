package cliente;

import interfacesComunes.Twitter;
import winterwell.jtwitter.OAuthSignpostClient;

public class TestTwitterServerConect {

	public static void main(String[] args){
		String JKEY="MTRvDLFJqnQ5W4VjIGob0w";
		String JSECRET="QXVQNJg4cywAk7xvBuSRT9BMPzb6yKHtijAvU0GV2is";
		String TKEY ="207417955-0z0e3I8GoOcjx4zb4hqJAYeoDoCfl1JQGyR9yD2g";
		String TSECRET="87UVVsVey5zQpjJe8cFzJswUiSwt9N2oUtqVt8NAC1M";


		OAuthSignpostClient client = new OAuthSignpostClient(JKEY, JSECRET, TKEY, TSECRET);
		Twitter jtwit = new winterwell.jtwitter.TwitterImpl("yourtwittername", client);
		jtwit.updateStatus("a ver ahora jeje");
	}
}
