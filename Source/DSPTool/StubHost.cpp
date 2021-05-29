// Copyright 2017 Dolphin Emulator Project
// Licensed under GPLv2+
// Refer to the license.txt file included.

// Stub implementation of the Host_* callbacks for DSPTool. These implementations
// do nothing except return default values when required.

#include <string>
#include <vector>

#include "Core/Host.h"

std::vector<std::string> Host_GetPreferredLocales()
{
  return {};
}
void Host_NotifyMapLoaded()
{
}
void Host_RefreshDSPDebuggerWindow()
{
}
void Host_Message(HostMessageID)
{
}
void Host_UpdateTitle(const std::string&)
{
}
void Host_UpdateDisasmDialog()
{
}
void Host_UpdateMainFrame()
{
}
void Host_RequestRenderWindowSize(int, int)
{
}
bool Host_RendererHasFocus()
{
  return false;
}
bool Host_RendererHasFullFocus()
{
  return false;
}
bool Host_RendererIsFullscreen()
{
  return false;
}
void Host_YieldToUI()
{
}
void Host_TitleChanged()
{
}
bool Host_UIBlocksControllerState()
{
  return false;
}
