package org.hope6537.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUpdateUtils {

	public FileUpdateUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Descirbe
	 * @Author Hope6537(赵鹏)
	 * @Params @param request
	 * @Params @param response
	 * @Params @param folder
	 * @Params @param nextStep
	 * @Params @return
	 * @SignDate 2014-5-2下午01:59:09
	 * @Version 0.9
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> updateImage(HttpServletRequest request,
			HttpServletResponse response, String folder, String nextStep) {
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<NameValuePair> arrayList2 = new ArrayList<NameValuePair>();
		try {

			response.setContentType("text/html; charset=UTF-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 获得磁盘文件条目工厂。
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 获取文件上传需要保存的路径，upload文件夹需存在。
			String path = request.getSession().getServletContext()
					.getRealPath("/" + folder);
			String lesspath = "//" + folder;
			// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
			factory.setRepository(new File(path));
			// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
			factory.setSizeThreshold(1024 * 1024);
			// 上传处理工具类（高水平API上传处理？）
			ServletFileUpload upload = new ServletFileUpload(factory);

			// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				// 获取表单属性名字。
				String name = item.getFieldName();
				// 如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if (item.isFormField()) {
					// 获取用户具体输入的字符串，
					String value = item.getString();
					arrayList2.add(new NameValuePair(name, value));
				}
				// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else {
					// 获取路径名
					String value = item.getName();
					// 取到最后一个反斜杠。
					int start = value.lastIndexOf("\\");
					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
					String filename = value.substring(start + 1);
					/*
					 * 第三方提供的方法直接写到文件中。 item.write(new File(path,filename));
					 */
					// 收到写到接收的文件中。
					OutputStream out = new FileOutputStream(new File(path,
							filename));
					InputStream in = item.getInputStream();

					arrayList.add(lesspath + "//" + filename);
					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("获取文件总量的容量:" + item.getSize());

					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
				}
			}
			request.setAttribute("PARAMS", arrayList2);
			request.setAttribute("FILEPATHS", arrayList);
			// UserinfoServiceServlet?SERVICE=UPDATESERVICE&&STEP=2
			request.getRequestDispatcher(nextStep).forward(request, response);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
