package ar.edu.itba.pf.newsvisualization.domain.model.request;

/**
 * Created by juanjosemarinelli on 9/27/16.
 */
public class ESQuery {
    private ESRange range;

    public ESQuery(String from, String to, String format) {
        this.range = new ESRange(from, to, format);
    }

    public ESRange getRange() {
        return range;
    }

    public class ESRange {
        private ESFecha fecha;

        public ESRange(String from, String to, String format) {
            this.fecha = new ESFecha(from, to, format);
        }

        public ESFecha getFecha() {
            return fecha;
        }

        public class ESFecha {
            private String gte;
            private String lte;
            private String format;

            public ESFecha(String gte, String lte, String format) {

                this.gte = gte;
                this.lte = lte;
                this.format = format;
            }

            public String getGte() {
                return gte;
            }

            public String getLte() {
                return lte;
            }

            public String getFormat() {
                return format;
            }
        }
    }
}
