package facades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.SymbolDTO;
import dtos.SymbolsDTO;
import entities.Flag;
import entities.Symbol;
import org.eclipse.persistence.platform.database.SybasePlatform;
import org.jsoup.select.Elements;
import utils.HttpUtils;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ValutaFacade {



    private static EntityManagerFactory emf;
    private static ValutaFacade instance;
    private static Gson GSON = new Gson();

    private ValutaFacade() {
    }

    public static ValutaFacade getValutaFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ValutaFacade();
        }
        return instance;
    }

    public void persistFlags(HashMap<String, Elements> flags){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        flags.forEach((k,v) -> {
            Flag flag = new Flag();
            flag.setCode(k);
            flag.setSvg(v.toString());
            em.persist(flag);
        });
        em.getTransaction().commit();
    }

    public SymbolsDTO getAllSymbolsFromDB() throws Exception {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Symbol> query = em.createQuery("SELECT s from Symbol s", Symbol.class);
        List<Symbol> list = query.getResultList();

        if (list.isEmpty()){
            throw new Exception("error");
        }
        return new SymbolsDTO(list);
    }

    public void updateValutaSymbols() throws IOException {
        EntityManager em = emf.createEntityManager();
        String rawJson = HttpUtils.fetchData("https://api.exchangerate.host/symbols");
        JsonObject obj = new JsonParser().parse(rawJson).getAsJsonObject();
        HashMap symbols = GSON.fromJson(obj.get("symbols"), HashMap.class);
            List<Symbol> ll = new ArrayList<>();
            em.getTransaction().begin();
          symbols.forEach((k,v) -> {
             String[] props = v.toString().split(",");
             String description = props[0];
              description = description.replaceAll("description=","");
              description = description.replace("{","");
             String code = props[1];
              code = code.replaceAll("code=","");
              code = code.replace("}","");
             Symbol s = new Symbol(code.trim(),description.trim());
             em.persist(s);

             ll.add(s);
          });

            em.getTransaction().commit();
    }

}
