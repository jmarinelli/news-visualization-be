package ar.edu.itba.pf.newsvisualization.domain.model.request.aggregators;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class TitlesAggregator {
    private static final List<String> EXCLUDES = Lists.newArrayList("a","acá","ahí","ajena","ajenas","ajeno","ajenos","al","algo","algún","alguna","algunas","alguno","algunos","allá","alli","allí","ambos","ampleamos","ante","antes","aquel","aquella","aquellas","aquello","aquellos","aqui","aquí","arriba","asi","atras","aun","aunque","bajo","bastante","bien","cabe","cada","casi","cierta","ciertas","cierto","ciertos","como","cómo","con","conmigo","conseguimos","conseguir","consigo","consigue","consiguen","consigues","contigo","contra","cual","cuales","cualquier","cualquiera","cualquieras","cuan","cuán","cuando","cuanta","cuánta","cuantas","cuántas","cuanto","cuánto","cuantos","cuántos","de","dejar","del","demás","demas","demasiada","demasiadas","demasiado","demasiados","dentro","desde","donde","dos","el","él","ella","ellas","ello","ellos","empleais","emplean","emplear","empleas","empleo","en","encima","entonces","entre","era","eramos","eran","eras","eres","es","esa","esas","ese","eso","esos","esta","estaba","estado","estais","estamos","estan","estar","estas","este","esto","estos","estoy","etc","fin","fue","fueron","fui","fuimos","gueno","ha","hace","haceis","hacemos","hacen","hacer","haces","hacia","hago","hasta","incluso","intenta","intentais","intentamos","intentan","intentar","intentas","intento","ir","jamás","junto","juntos","la","largo","las","lo","los","mas","más","me","menos","mi","mía","mia","mias","mientras","mio","mío","mios","mis","misma","mismas","mismo","mismos","modo","mucha","muchas","muchísima","muchísimas","muchísimo","muchísimos","mucho","muchos","muy","nada","ni","ningun","ninguna","ningunas","ninguno","ningunos","no","nos","nosotras","nosotros","nuestra","nuestras","nuestro","nuestros","nunca","os","otra","otras","otro","otros","para","parecer","pero","poca","pocas","poco","pocos","podeis","podemos","poder","podria","podriais","podriamos","podrian","podrias","por","por qué","porque","primero","primero desde","puede","pueden","puedo","pues","que","qué","querer","quien","quién","quienes","quienesquiera","quienquiera","quiza","quizas","sabe","sabeis","sabemos","saben","saber","sabes","se","segun","ser","si","sí","siempre","siendo","sin","sín","sino","so","sobre","sois","solamente","solo","somos","soy","sr","sra","sres","sta","su","sus","suya","suyas","suyo","suyos","tal","tales","también","tambien","tampoco","tan","tanta","tantas","tanto","tantos","te","teneis","tenemos","tener","tengo","ti","tiempo","tiene","tienen","toda","todas","todo","todos","tomar","trabaja","trabajais","trabajamos","trabajan","trabajar","trabajas","trabajo","tras","tú","tu","tus","tuya","tuyo","tuyos","ultimo","un","una","unas","uno","unos","usa","usais","usamos","usan","usar","usas","uso","usted","ustedes","va","vais","valor","vamos","van","varias","varios","vaya","verdad","verdadera","vosotras","vosotros","voy","vuestra","vuestras","vuestro","vuestros","y","ya","yo", "le", "2015", "2016", "hay", "años", "año", "hoy", "nuevo", "argentina", "san", "está", "son", "10", "vez", "día", "30", "20", "durante", "6", "dice", "así", "e", "5", "cinco", "o");

    private ESTag titles;

    public TitlesAggregator() {
        this.titles = new ESTag();
    }

    public ESTag getTitles() {
        return titles;
    }

    public class ESTag {
        private ESTerm terms;

        public ESTag() {
            this.terms = new ESTerm();
        }

        public ESTerm getTerms() {
            return terms;
        }

        public class ESTerm {
            private String field;
            private List<String> exclude;
            private Integer size;

            public ESTerm() {
                this.field = "title";
                this.exclude = EXCLUDES;
                this.size = 2000;
            }

            public String getField() {
                return field;
            }

            public List<String> getExclude() {
                return exclude;
            }

            public Integer getSize() {
                return size;
            }
        }
    }
}
