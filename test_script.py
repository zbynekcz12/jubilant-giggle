import tensorflow as tf
import numpy as np
import os
from PIL import Image

# Načtení TensorFlow Lite modelu
interpreter = tf.lite.Interpreter(model_path="path/to/your/model.tflite")
interpreter.allocate_tensors()

# Načtení datasetu
def load_dataset(dataset_path):
    dataset = []
    for filename in os.listdir(dataset_path):
        if filename.endswith(".png"):
            image = Image.open(os.path.join(dataset_path, filename))
            image = image.resize((input_shape[1], input_shape[2])) # Resize na velikost modelu
            dataset.append(np.array(image))
    return np.array(dataset)

dataset_path = "path/to/your/dataset"
dataset = load_dataset(dataset_path)

# Získání vstupního a výstupního tenzoru
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

input_shape = input_details[0]['shape']

# Provedení inference na všech vzorcích
results = []
for i, input_data in enumerate(dataset):
    input_data = np.expand_dims(input_data, axis=0).astype(np.float32)
    interpreter.set_tensor(input_details[0]['index'], input_data)
    interpreter.invoke()
    output_data = interpreter.get_tensor(output_details[0]['index'])
    results.append(output_data)
    print(f"Processed sample {i + 1}/{len(dataset)}")

# Uložení výsledků
np.save("inference_results.npy", results)
print("Test completed and results saved.")