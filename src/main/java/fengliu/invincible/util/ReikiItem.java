package fengliu.invincible.util;

public interface ReikiItem{
    public int getReiki();
    public int getMaxReiki();
    public int getInitialReiki();
    public int getTargetReiki();
    public int getMaxInjectionReiki();
    public boolean canInjectionReiki();
    public void settings(ReikiSettings settings);

    public class ReikiSettings {
        public int MaxReiki = 0;
        public int InitialReiki = -1;
        public int Reiki = 0;
        public int TargetReiki = 0;
        public int MaxInjectionReiki = 0;
        public boolean CanInjectionReiki = false;

        public ReikiSettings maxReiki(int maxReiki){
            MaxReiki = maxReiki;
            return this;
        }

        public ReikiSettings initialReiki(int initialReiki){
            InitialReiki = initialReiki;
            Reiki = initialReiki;
            return this;
        }

        public ReikiSettings canInjectionReiki(boolean canInjectionReiki){
            CanInjectionReiki = canInjectionReiki;
            return this;
        }

        public ReikiSettings injectionReikTarget(int targetReiki){
            TargetReiki = targetReiki;
            return this;
        }

        public ReikiSettings maxInjectionReiki(int maxInjectionReiki){
            MaxInjectionReiki = maxInjectionReiki;
            return this;
        }
    }
}
