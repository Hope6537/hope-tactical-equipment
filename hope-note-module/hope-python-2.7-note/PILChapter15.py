# encoding:UTF-8
import random

import ImageDraw
import ImageFilter
import ImageFont

__author__ = 'Hope6537'

import Image

# 打开一个jpg图像文件，注意路径要改成你自己的:
im = Image.open('D:/miku.jpg')
# 获得图像尺寸:
w, h = im.size
# 缩放到50%:
im.thumbnail((w // 2, h // 2))
# 把缩放后的图像用jpeg格式保存:
im.save('D:/miku2.jpg', 'jpeg')

im = Image.open('D:/miku.jpg')
# 图像滤镜化
im2 = im.filter(ImageFilter.BLUR)
im2.save('D:/miku3.jpg', 'jpeg')


# 同时提供了一些绘图方法

# 随机字母:
def rndChar():
    return chr(random.randint(65, 90))


# 随机颜色1:
def rndColor():
    return (random.randint(64, 255), random.randint(64, 255), random.randint(64, 255))


# 随机颜色2:
def rndColor2():
    return (random.randint(32, 127), random.randint(32, 127), random.randint(32, 127))


# 480 x 80:
width = 80 * 5
height = 80
image = Image.new('RGB', (width, height), (255, 255, 255))
# 创建Font对象:
font = ImageFont.truetype('Arial.ttf', 42)
# 创建Draw对象:
draw = ImageDraw.Draw(image)
# 填充每个像素:
for x in range(width):
    for y in range(height):
        draw.point((x, y), fill=rndColor())
# 输出文字:
for t in range(6):
    draw.text((60 * t + 10, 10), rndChar(), font=font, fill=rndColor2())
# 模糊:
image = image.filter(ImageFilter.BLUR)
image.save('D:/code.jpg', 'jpeg');
