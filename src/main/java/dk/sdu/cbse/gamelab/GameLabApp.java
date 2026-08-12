package dk.sdu.cbse.gamelab;
import javafx.animation.AnimationTimer; import javafx.application.Application; import javafx.scene.*; import javafx.scene.canvas.*; import javafx.scene.paint.Color; import javafx.stage.Stage; import java.util.*;
public class GameLabApp extends Application {
 public interface IGamePluginService{void start(List<Entity> e);void stop(List<Entity> e);} public interface IEntityProcessingService{void process(List<Entity> e,double dt);} public interface IPostEntityProcessingService{void process(List<Entity> e);}
 static class Entity{String type;double x,y,dx,dy,r;Entity(String t,double x,double y,double r){this.type=t;this.x=x;this.y=y;this.r=r;}}
 private final List<Entity> entities=new ArrayList<>();
 public void start(Stage stage){Canvas c=new Canvas(800,600);Scene s=new Scene(new Group(c));stage.setScene(s);stage.setTitle("GameLab - JavaFX");stage.show(); Entity p=new Entity("PLAYER",400,300,15);entities.add(p);Random rnd=new Random();for(int i=0;i<7;i++){Entity a=new Entity("ASTEROID",rnd.nextInt(800),rnd.nextInt(600),20);a.dx=-30+rnd.nextDouble()*60;a.dy=-30+rnd.nextDouble()*60;entities.add(a);} new AnimationTimer(){long last;public void handle(long n){double dt=last==0?0:(n-last)/1e9;last=n;for(Entity e:entities){e.x=(e.x+e.dx*dt+800)%800;e.y=(e.y+e.dy*dt+600)%600;}GraphicsContext g=c.getGraphicsContext2D();g.setFill(Color.BLACK);g.fillRect(0,0,800,600);for(Entity e:entities){g.setStroke(e.type.equals("PLAYER")?Color.CYAN:Color.GRAY);g.strokeOval(e.x-e.r,e.y-e.r,e.r*2,e.r*2);}}}.start();}
 public static void main(String[] a){launch(a);} }
