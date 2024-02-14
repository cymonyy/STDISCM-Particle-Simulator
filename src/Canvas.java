import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Canvas extends JPanel {

//    private Queue<Particle> particlesQueue = new LinkedList<>();
    private List<Particle> particles = new ArrayList<>();
    static int count = 0;
    static int MAX_SIZE = 0;

    private final Lock lock = new ReentrantLock();


//    private Queue<Configuration> configurations = new LinkedList<>();

//    private static Lock configLock = new ReentrantLock();
//    private static Condition configCondition = configLock.newCondition();

//    private static Lock particlesLock = new ReentrantLock();
//    private static Condition particleCondition = particlesLock.newCondition();


    private List<Thread> producers = new ArrayList<>();
    private List<Thread> workers = new ArrayList<>();


    public Canvas(){
        this.setLayout(null);
        this.setBackground(new Color(13, 21, 23));
        this.setPreferredSize(new Dimension(1280, 720));

//        //Create Producers
//        for (int i = 0; i < 2; i++) {
//            Thread thread = new Thread(new Producer(configurations));
//            producers.add(thread);
//            thread.start();
//        }
//
        //Create Workers
        for (int i = 0; i < 6; i++) {
            Thread thread = new Thread(new Worker());
            workers.add(thread);
            thread.start();
        }

//        while (true)
//            this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle particle : particles) {
            g.setColor(Color.white);
            g.fillOval(particle.getX(), 720-particle.getY(), 5, 5);
        }
    }

    public List<Thread> getThreads(){
        List<Thread> threads = new ArrayList<>(producers);
        threads.addAll(workers);
        return threads;
    }

    public void addParticles(List<Particle> added){
        lock.lock();
        try{
            MAX_SIZE = particles.size();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
            particles.addAll(added);
        }
    }

    class Worker implements Runnable {



        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                lock.lock();
                try{
                    if(MAX_SIZE > 0){
                        if (count >= MAX_SIZE){
                            count = 0;
                        }
                        else{
                            Particle particle = particles.get(count);
                            particle.move();
                            repaint();
                            Thread.sleep(11);
                            count++;
                        }
                    }

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        }
    }







//    public void addConfigurations(Queue<Configuration> configurations){
//        configLock.lock();
//        try{
//            this.configurations.addAll(configurations);
//            configCondition.signalAll();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            configLock.unlock();
//        }
//    }




//    public void setParticleTasks(Queue<AddParticleTask> particleTasks){
//        synchronized (particleTasksLock){
//            this.particleTasks.addAll(particleTasks);
//            particleTasksLock.notifyAll();
//        }
//    }

//    class Worker implements Runnable {
//        private List<Particle> particles;
////        private Particle temp;
//
//
//        public Worker(List<Particle> particles) {
//            this.particles = particles;
//        }
//
//        @Override
//        public void run() {
//            try{
////                while (!Thread.currentThread().isInterrupted()){
////                    particlesLock.lock();
////                    try{
////                        if(!particles.isEmpty()){
////                            if(count >= particles.size())
////                                count = 0;
////                            else {
////                                Particle particle = particles.get(count);
////                                particle.move();
////                                repaint();
////                                Thread.sleep(11);
////                                count++;
////                            }
////                        }
////
////
//////                        while (particles.isEmpty())
//////                            particleCondition.await();
//////
////
////
////
////
////
//////                        temp = particles.poll();
//////                        temp.move();
//////                        particles.add(temp);
////
//////                        repaint();
//////                        Thread.sleep(11);
////
////                    }catch (Exception e){
//////                        particles.add(temp);
//////                        temp = null;
////                        e.printStackTrace();
////                        Thread.currentThread().interrupt();
////                    } finally {
////                        particlesLock.unlock();
////                    }
////                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//    }

//    class Producer implements Runnable {
//
//        private Queue<Configuration> configurations;
////        private List<Particle> particles;
//
//        public Producer(Queue<Configuration> configurations) {
//            this.configurations = configurations;
////            this.particles = particles;
//        }
//
//        @Override
//        public void run() {
////            try {
////                while (!Thread.currentThread().isInterrupted()){
////                    configLock.lock();
////                    try{
////
////                        //wait until configurations.size > 0
////                        while (configurations.isEmpty())
////                            configCondition.await();
////
////                        //make particles
////                        Configuration temp = configurations.peek();
////                        System.out.println(temp);
////                        makeParticle(temp);
////
////                        temp.incrementFinished();
////                        if (temp.isFinished()){
////                            temp = configurations.poll();
////                        }
////
////                        System.out.println(configurations.isEmpty());
////
////                    } catch (Exception e){
////                        e.printStackTrace();
////                        Thread.currentThread().interrupt();
////
////                    } finally {
////                        configLock.unlock();
////                    }
////
////                }
////
////            } catch (Exception e){
////                e.printStackTrace();
////            }
//        }
//
//
//
//        private void makeParticle(Configuration configuration){
//            particlesLock.lock();
//            try{
//                Particle particle = new Particle(
//                        configuration.getX(),
//                        configuration.getY(),
//                        configuration.getAngle(),
//                        configuration.getVelocity()
//                );
//
////                particlesQueue.add(particle);
//                particles.add(particle);
////                particleCondition.signalAll();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            finally {
//                particlesLock.unlock();
//            }
//        }
//    }


















}
