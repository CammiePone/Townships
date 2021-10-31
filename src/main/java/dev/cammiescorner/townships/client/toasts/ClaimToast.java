package dev.cammiescorner.townships.client.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClaimToast implements Toast {
	private final Text text;

	public ClaimToast(Text text) {
		this.text = text;
	}

	@Override
	public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		manager.drawTexture(matrices, 0, 0, 0, 0, 160, 32);
		manager.getGame().textRenderer.draw(matrices, text, (getWidth() - manager.getGame().textRenderer.getWidth(text)) / 2F, 12, -256);
		return startTime < 1250L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
	}
}
