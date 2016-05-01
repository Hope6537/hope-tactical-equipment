# encoding:utf-8
import os

from PIL import Image
from pylab import *


def open_image(path):
    """打开图像"""
    return Image.open(path)


def open_image_and_convert_black(path):
    """打开图像并转换灰度"""
    return Image.open(path).convert("L")


def create_thumbnail(image):
    """创建缩略图"""
    return image.thumbnail((128, 128))


def convert_image_format(file, target_format_suffix):
    """转换图像至目标格式"""
    output = os.path.splitext(file)[0] + '.' + target_format_suffix;
    try:
        Image.open(file).save(output)
    except IOError:
        print("cannot convert image")


def cut_image_section(image, box=(100.100, 400, 400)):
    return image.crop(box)


def paste_image_section(image, clip, box=(100.100, 400, 400)):
    return image.paste(clip.transpose(Image.ROTATE_180), box)


def resize_image(image, new_size=(128, 128)):
    return image.resize(new_size)


def rotate_image(image, degree=180):
    return image.rotate(degree)


def write_point_line_image(image_list):
    """
    使用matplotlib进行绘制
    :param image_list:
    :return:
    """
    # pylab
    imshow(image_list)
    # 一些点
    x = [100, 100, 400, 400]
    y = [200, 500, 200, 500]
    # 使用红色星状标记绘制点
    plot(x, y, 'r*')
    # 绘制链接前两个点的线
    plot(x[:2], y[:2])

    title('Plotting: Image')
    axis('off')
    show()


write_point_line_image(array(open_image('./testImage.jpg')))
