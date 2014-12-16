# -*- coding: utf-8 -*-
import json
import sys, os
import jinja2
import pprint
import argparse

def fillTemplate(templatePath, articlePath, dataPath, outputPath):
	cwd  = os.getcwd()
	templateLoader = jinja2.FileSystemLoader( searchpath=cwd )
	templateEnv = jinja2.Environment( loader=templateLoader )
	template = templateEnv.get_template(templatePath)

	with open(dataPath, 'rb') as fp:
	    reportData = json.loads(fp.read())

	with open(articlePath, 'rb') as fp:
	    articleData = json.loads(fp.read())

	# pprint.pprint(reportData)
	# pprint.pprint(articleData)

	
	# Specify any input variables to the template as a dictionary.
	templateVars = { 
		"tweet" : {
			"text": reportData['tweet']['text'],
			"screenname": reportData['tweet']['screenname'],
			"date": reportData['tweet']['date'],
		},
		"article" : {
			"title": articleData['title'],
			"date": articleData['published'],
			"body": articleData['body'],
			"description": articleData['description'],
			"image": articleData['image'],
		},
		"analysis" : {
			"languages": {
				"columns": [[str(col[0]), col[1]] for col in  reportData['languages']['columns']],
				"groups": [str(gr) for gr in reportData['languages']['groups']],
			},
			"themes": {
				"groups": [str(gr.encode('utf-8')) for gr in reportData['themes']['groups']],
				"columns": [[str(col[0].encode('utf-8')), col[1]] for col in  reportData['themes']['columns'] if col[1] > 10],
			},
			"entities": {
				"columns": [[str(col[0].encode('utf-8')), col[1]] for col in  reportData['entities']['columns'] if col[1] > 10],
				"groups": [str(gr.encode('utf-8')) for gr in reportData['entities']['groups']],
			},
		},
	}

	# Finally, process the template to produce our final text.
	outputText = template.render( templateVars )

	# write to file
	with open(outputPath, 'wb') as of:
		of.write(outputText)
		print "Written output to {}".format(outputPath)

def main():
	parser = argparse.ArgumentParser(description='fill HTML template with data from JSON')
	parser.add_argument('templatePath', 
		help='the path to the HTML (.jinja) template')
	parser.add_argument('articlePath', 
		help='the path to the downloaded BBC article in JSON')
	parser.add_argument('dataPath', 
		help='the path to the analysed data in JSON')
	parser.add_argument('outputPath',
		help='HTML path to write result to')
	args = parser.parse_args()

	# articleURL = "http://www.bbc.co.uk/news/world-australia-30473983"
	fillTemplate(args.templatePath, args.articlePath, args.dataPath, args.outputPath)

if __name__ == '__main__':
	main()
