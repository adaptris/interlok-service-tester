package com.adaptris.tester.runtime.messages.assertion;

import com.adaptris.core.fs.FsHelper;
import com.adaptris.fs.FsException;
import com.adaptris.fs.FsWorker;
import com.adaptris.fs.NioWorker;
import com.adaptris.tester.runtime.ServiceTestException;
import com.adaptris.tester.runtime.messages.TestMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals file contents.
 *
 * <p>Assertions are used to validate the returned message is expected.</p>
 *
 * @service-test-config assert-payload-equals
 */
@XStreamAlias("assert-payload-equals-file")
public class AssertPayloadEqualsFile implements Assertion {

  private String uniqueId;
  private String file;

  private transient FsWorker fsWorker = new NioWorker();

  public AssertPayloadEqualsFile(){
  }

  public AssertPayloadEqualsFile(String file){
    setFile(file);
  }

  @Override
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  @Override
  public String getUniqueId() {
    return uniqueId;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Checks if {@link com.adaptris.tester.runtime.messages.TestMessage#getPayload()} equals contents of {@link #getFile()}.
   * @param actual Message resulting from text execution
   * @return Return result of assertion using {@link AssertionResult}
   */
  @Override
  public final AssertionResult execute(TestMessage actual) throws ServiceTestException {
    try {
      URL url = FsHelper.createUrlFromString(file, true);
      File fileToRead = FsHelper.createFileReference(url);
      final byte[] fileContents = fsWorker.get(fileToRead);
      return new AssertionResult(getUniqueId(), "assert-payload-equals-file", new String(fileContents).equals(actual.getPayload()));
    } catch (IOException | URISyntaxException | FsException e) {
      throw new ServiceTestException(e);
    }
  }

  @Override
  public String expected() {
    return "Payload file: " + getFile();
  }

  @Override
  public boolean showReturnedMessage() {
    return true;
  }
}
