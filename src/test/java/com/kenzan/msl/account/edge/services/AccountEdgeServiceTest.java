package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.account.client.services.CassandraAccountService;
import com.kenzan.msl.account.edge.TestConstants;
import io.swagger.model.MyLibrary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CassandraAccountService.class})
public class AccountEdgeServiceTest {

  private TestConstants tc = TestConstants.getInstance();
  private CassandraAccountService cassandraAccountService;

  @Mock
  private LibraryService libraryService;

  @Before
  public void init() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMock.mockStatic(CassandraAccountService.class);
    cassandraAccountService = createMock(CassandraAccountService.class);
    PowerMock.expectNew(CassandraAccountService.class).andReturn(cassandraAccountService);
    expect(CassandraAccountService.getInstance()).andReturn(cassandraAccountService).anyTimes();
  }

  @Test
  public void testGetMyLibrary() throws Exception {
    Mockito.when(libraryService.get(cassandraAccountService, tc.USER_ID.toString())).thenReturn(
        new MyLibrary());
    PowerMock.replayAll();

    /* ************************************************** */

    AccountEdgeService accountEdgeService = new AccountEdgeService(libraryService);
    Observable<MyLibrary> result = accountEdgeService.getMyLibrary(tc.USER_ID.toString());
    assertNotNull(result.toBlocking().first());
  }

  @Test
  public void testAddToLibrary() {
    PowerMock.replayAll();
    AccountEdgeService accountEdgeService = new AccountEdgeService(libraryService);
    accountEdgeService.addToLibrary(tc.ARTIST_UUID.toString(), tc.USER_ID.toString(), "Artist");
    Mockito.verify(libraryService, atLeastOnce()).add(cassandraAccountService,
        tc.ARTIST_UUID.toString(), tc.USER_ID.toString(), "Artist");
  }

  @Test(expected = RuntimeException.class)
  public void testAddToLibraryException() {
    PowerMock.replayAll();
    AccountEdgeService accountEdgeService = new AccountEdgeService(null);
    accountEdgeService.addToLibrary(tc.ARTIST_UUID.toString(), tc.USER_ID.toString(), "Artist");
  }

  @Test
  public void testRemoveFromLibrary() {
    PowerMock.replayAll();
    AccountEdgeService accountEdgeService = new AccountEdgeService(libraryService);
    accountEdgeService.removeFromLibrary(tc.ARTIST_UUID.toString(),
        tc.FAVORITES_TIMESTAMP.toString(), tc.USER_ID.toString(), "Artist");
    Mockito.verify(libraryService, atLeastOnce()).remove(cassandraAccountService,
        tc.ARTIST_UUID.toString(), tc.FAVORITES_TIMESTAMP.toString(), tc.USER_ID.toString(),
        "Artist");
  }

  @Test(expected = RuntimeException.class)
  public void testRemoveFromLibraryException() {
    PowerMock.replayAll();
    AccountEdgeService accountEdgeService = new AccountEdgeService(null);
    accountEdgeService.removeFromLibrary(tc.ARTIST_UUID.toString(),
        tc.FAVORITES_TIMESTAMP.toString(), tc.USER_ID.toString(), "Artist");
  }
}
