package br.com.schioDev.jogot9.fase2.engines;

import br.com.schioDev.jogot9.fase2.obj.Shoot;

public interface ShootEngineDelegate {
	public void createShoot(Shoot shoot);

	public void removeShoot(Shoot shoot);
}
