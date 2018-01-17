@echo "Generating image..."
text2image --fonts_dir=C:\Windows\Fonts --font="Iskoola Pota"  --resolution=400 --ptsize=18 --text=sin.testtext.txt  --outputbase=sin.testtext  --fontconfig_tmpdir C:\Windows\Temp 

@echo "Starting recognition..."
tesseract sin.testtext.tif sin.outtext --oem 1 -l sin --tessdata-dir "C:\Program Files (x86)\Tesseract-OCR\tessdata" 

pause