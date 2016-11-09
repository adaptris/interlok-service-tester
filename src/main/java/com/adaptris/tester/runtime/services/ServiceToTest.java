package com.adaptris.tester.runtime.services;

import com.adaptris.tester.runtime.services.preprocessor.PreprocessorException;
import com.adaptris.tester.runtime.services.preprocessor.Preprocessor;
import com.adaptris.tester.runtime.services.sources.Source;
import com.adaptris.tester.runtime.services.sources.SourceException;

import java.util.ArrayList;
import java.util.List;

public class ServiceToTest {

  private Source source;
  private List<Preprocessor> preprocessors;

  public ServiceToTest(){
    setPreprocessors(new ArrayList<Preprocessor>());
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public Source getSource() {
    return source;
  }

  public void setPreprocessors(List<Preprocessor> preprocessors) {
    this.preprocessors = preprocessors;
  }

  public List<Preprocessor> getPreprocessors() {
    return preprocessors;
  }

  public void addPreprocessor(Preprocessor preprocessor){
    this.preprocessors.add(preprocessor);
  }

  public String getProcessedSource() throws PreprocessorException, SourceException {
    String result = getSource().getSource();
    for (Preprocessor preprocessor : getPreprocessors()) {
      result = preprocessor.execute(result);
    }
    return result;
  }
}
