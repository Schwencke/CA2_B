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

import javax.enterprise.inject.Typed;
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

    public Flag getFlagByCode(String code){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Flag> query = em.createQuery("select f from Flag f where f.code = :code", Flag.class);
        query.setParameter("code", code);
        Flag flag;
        flag = query.getSingleResult();
        return flag;
    }

    public List<Flag> getFlags() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Flag> query = em.createQuery("select f from Flag f", Flag.class);
        List<Flag> flaglist;
        flaglist = query.getResultList();
        return flaglist;
    }

    public void persistFlags(HashMap<String, String> flags){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        flags.forEach((k,v) -> {
            Flag flag = new Flag();
            flag.setCode(k);
            flag.setSvg(v);
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
