package phasebook.lottery;

import javax.ejb.Remote;

@Remote
public interface LotteryRemote {
	
	void scheduleTimer(long milliseconds);
	public String nextDrawDate();

}
