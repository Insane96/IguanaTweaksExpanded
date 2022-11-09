package insane96mcp.iguanatweaksreborn.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import insane96mcp.iguanatweaksreborn.IguanaTweaksReborn;
import insane96mcp.iguanatweaksreborn.data.ITDataReloadListener;
import insane96mcp.iguanatweaksreborn.utils.LogHelper;
import insane96mcp.insanelib.base.Feature;
import insane96mcp.insanelib.base.Module;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;

public class ITFeature extends Feature {
    public ITFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
        jsonConfigFolder = new File(IguanaTweaksReborn.CONFIG_FOLDER + "/" + module.getName() + "/" + this.getName());
        if (!jsonConfigFolder.exists()) {
            if (!jsonConfigFolder.mkdir()) {
                LogHelper.warn("Failed to create %s json config folder", this.getName());
            }
        }
        ITDataReloadListener.INSTANCE.registerJsonConfigFeature(this);
    }

    protected final File jsonConfigFolder;

    public void loadJsonConfigs() {
        if (!jsonConfigFolder.exists())
            jsonConfigFolder.mkdirs();
    }

    protected <T> void loadAndReadFile(String fileName, List<T> list, final List<T> defaultList, Type listType) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(jsonConfigFolder, fileName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new Exception("File#createNewFile failed");
                }
                String json = gson.toJson(defaultList, listType);
                Files.write(file.toPath(), json.getBytes());
            }
            catch (Exception e) {
                LogHelper.error("Failed to create default Json %s: %s", FilenameUtils.removeExtension(file.getName()), e.getMessage());
            }
        }

        list.clear();
        try {
            FileReader fileReader = new FileReader(file);
            List<T> listRead = gson.fromJson(fileReader, listType);
            list.addAll(listRead);
        }
        catch (JsonSyntaxException e) {
            LogHelper.error("Parsing error loading Json %s: %s", FilenameUtils.removeExtension(file.getName()), e.getMessage());
        }
        catch (Exception e) {
            LogHelper.error("Failed loading Json %s: %s", FilenameUtils.removeExtension(file.getName()), e.getMessage());
        }
    }
}
