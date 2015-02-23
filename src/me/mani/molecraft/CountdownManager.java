package me.mani.molecraft;

import java.util.function.Consumer;

import org.bukkit.scheduler.BukkitRunnable;

public class CountdownManager extends Manager {
	
	/**
    * Starts a countdown
    *
    * @param consumer The consumer where the event will land
    * @param from The time where the countdown starts
    * @param to The time where the contdown ends
    * @param interval the interval between steps
    */
	public static Countdown createCountdown(Consumer<CountdownCountEvent> countConsumer, Consumer<CountdownFinishEvent> finishConsumer, int from, int to, long interval) {
		Countdown c = new Countdown(from, to, countConsumer, finishConsumer);
		c.runTaskTimer(MoleCraft.getInstance(), 0L, interval);
		return c;
	}
	
	public static class Countdown extends BukkitRunnable {
		
		private int from;
		private int to;
		private Consumer<CountdownCountEvent> countConsumer;
		private Consumer<CountdownFinishEvent> finishConsumer;	
		private boolean downcounting;
		
		public Countdown(int from, int to, Consumer<CountdownCountEvent> countConsumer, Consumer<CountdownFinishEvent> finishConsumer) {
			this.from = from;
			this.to = to;
			this.countConsumer = countConsumer;
			this.finishConsumer = finishConsumer;		
			this.downcounting = (from > to);
		}
		
		
		
		@Override
		public void run() {
			CountdownCountEvent ev = new CountdownCountEvent(from);
			countConsumer.accept(ev);
			Messenger.sendAll(ev.hasMessage() ? ev.getMessage() : null);
			Effects.playAll(ev.hasSound() ? ev.getSound() : null);
			if (from == to)
				stop(false);
			else
				if (downcounting)
					from--;
				else
					from++;
		}	
		
		public void stop(boolean force) {
			this.cancel();
			if (!force)
				finishConsumer.accept(new CountdownFinishEvent());
		}

	}
	
	public static class CountdownEvent {}
}
