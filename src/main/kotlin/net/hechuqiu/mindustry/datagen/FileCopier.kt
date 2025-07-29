package net.hechuqiu.mindustry.datagen

import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.concurrent.CompletableFuture

class FileCopier : DataProvider {
    // 获取Logger实例
    private val LOGGER: Logger = LoggerFactory.getLogger(FileCopier::class.java)

    fun copyBbmodel() {
        val sourceDir = Paths.get("../bbmodel")
        val resourcesDir = Paths.get("../src/main/resources/assets/mindustry")

        LOGGER.info("Starting bbmodel files copy process...")
        LOGGER.debug("Source directory: {}", sourceDir.toAbsolutePath())
        LOGGER.debug("Target base directory: {}", resourcesDir.toAbsolutePath())

        try {
            // Walk through all files in bbmodel directory
            Files.walk(sourceDir).forEach { sourceFile ->
                if (!Files.isDirectory(sourceFile)) {
                    val relativePath = sourceDir.relativize(sourceFile)
                    val fileName = sourceFile.fileName.toString()

                    when {
                        fileName.endsWith(".animation.json") -> {
                            val target = resourcesDir.resolve("animations").resolve(relativePath)
                            copyFile(sourceFile, target)
                            LOGGER.debug("Copied animation file: {} -> {}", sourceFile, target)
                        }

                        fileName.endsWith(".geo.json") -> {
                            val target = resourcesDir.resolve("geo").resolve(relativePath)
                            copyFile(sourceFile, target)
                            LOGGER.debug("Copied geo file: {} -> {}", sourceFile, target)
                        }

                        fileName.endsWith(".png") -> {
                            val target = resourcesDir.resolve("textures").resolve(relativePath)
                            copyFile(sourceFile, target)
                            LOGGER.debug("Copied texture file: {} -> {}", sourceFile, target)
                        }
                    }
                }
            }
            LOGGER.info("bbmodel files copy completed successfully")
        } catch (e: Exception) {
            LOGGER.error("Error during bbmodel files copy process", e)
        }
    }

    private fun copyFile(source: Path, target: Path) {
        try {
            // Create parent directories if they don't exist
            Files.createDirectories(target.parent)
            LOGGER.trace("Created directories: {}", target.parent)

            // Copy file with overwrite option
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
            LOGGER.trace("Copied file: {} -> {}", source, target)
        } catch (e: Exception) {
            LOGGER.error("Failed to copy file: ${source.fileName}", e)
            throw e  // 重新抛出异常让上层处理
        }
    }

    override fun run(output: CachedOutput): CompletableFuture<*> {
        copyBbmodel()
        return CompletableFuture.completedFuture(null)
    }

    override fun getName(): String = "FileCopier"
}