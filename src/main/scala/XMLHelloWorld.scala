import java.net.URL

import com.rometools.rome.io.{SyndFeedInput, XmlReader}

object XMLHelloWorld {
  def main(args: Array[String]): Unit = {
    val xml = <hello>world</hello>
    println(xml)

        var ok = false;

      try {
        val feedUrl = new URL("http://feeds.reuters.com/reuters/topNews");

        val input = new SyndFeedInput();
        val feed = input.build(new XmlReader(feedUrl));

        System.out.println(feed);

        ok = true;
      }
      catch  {
        case ex:Exception =>
          ex.printStackTrace();
          println("ERROR: "+ex.getMessage());
      }
    }
}
