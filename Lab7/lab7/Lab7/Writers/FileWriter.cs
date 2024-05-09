using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab7.Writers
{
    public class FileWriter : IWriter
    {
        public string Path { get; }
        public FileWriter(string path)
        {
            Path = path;
        }
        public void Write(string text)
        {
            using (StreamWriter writer = new StreamWriter(Path))
            {
                writer.WriteLine(text, true);
            }
        }
    }
}
